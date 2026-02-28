package com.github.sugunasriram.fisloanlibv4.fiscode.network.sse

import android.util.Log
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.EOFException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection

class SSEClient(private val sseUrl: String, private val scope: CoroutineScope) {

    private var sseJob: Job? = null

    private val _events = MutableStateFlow("")
    val events: StateFlow<String> = _events

    private lateinit var client: OkHttpClient
    private var call: Call? = null

    // -----------------------------
    // OkHttp SSE (recommended)
    // -----------------------------
    fun start() {
        if (sseJob?.isActive == true) {
            Log.w("SSEClient", "SSE already running. Ignoring start().")
            return
        }
        stop()
        Log.d("SSEClient", "Starting new SSE connection...")
        sseJob = scope.launch(Dispatchers.IO) { runSse() }
    }

    fun stop() {
        sseJob?.cancel()
        sseJob = null
        call?.cancel()
        call = null
        Log.d("SSEClient", "SSE Disconnected")
    }

    private suspend fun runSse() {
        var retryCount = 0
        val maxRetries = 5
        val baseDelay = 3000L

        while (scope.isActive) {
            val accessToken = TokenManager.read("accessToken")
            val bearerToken = accessToken?.let { "Bearer $it" }

            if (bearerToken.isNullOrBlank()) {
                Log.d("SSEClient", "No token found. Cannot connect.")
                break
            }

            client = OkHttpClient.Builder()
                // SSE streams are long lived; avoid client-level timeouts
                .callTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

            val request = Request.Builder()
                .url(sseUrl)
                .addHeader("Authorization", bearerToken)
                .addHeader("Accept", "text/event-stream")
                .build()

            call = client.newCall(request)

            try {
                val response = call!!.execute()

                if (!response.isSuccessful) {
                    if (response.code == 401) {
                        Log.d("SSEClient", "Got 401. Refreshing token...")
                        val refreshed = ApiRepository.handleAuthGetAccessTokenApi()
                        response.close()

                        if (refreshed) {
                            Log.d("SSEClient", "Token refreshed. Reconnecting SSE...")
                            retryCount = 0
                            continue
                        } else {
                            Log.d("SSEClient", "Token expired. Cannot reconnect.")
                            break
                        }
                    } else {
                        Log.d("SSEClient", "SSE connection failed: ${response.code}")
                        response.close()
                        break
                    }
                }

                Log.d("SSEClient", "Connected to SSE")
                retryCount = 0

                response.body?.charStream()?.use { reader ->
                    val bufferedReader = BufferedReader(reader)
                    var line: String?

                    while (scope.isActive) {
                        line = bufferedReader.readLine()
                        if (line == null) {
                            Log.d("SSEClient", "Stream ended. Restarting...")
                            break
                        }

                        // Standard SSE: "data: ...."
                        if (line.startsWith("data:")) {
                            val eventData = line.removePrefix("data:").trim()
                            _events.value = eventData
                            Log.wtf("SSEClient", "data : $eventData")
                        }
                    }
                }

                response.close()
                // loop continues => reconnect

            } catch (e: Exception) {
                // common reconnect cases
                if (e is EOFException) {
                    Log.d("SSEClient", "EOFException: ${e.message}. Reconnecting...")
                    delay(3000)
                    continue
                }
                if (e.message == "Socket closed") {
                    Log.d("SSEClient", "Socket closed. Stopping.")
                    break
                }

                retryCount++
                val delayTime = baseDelay * (1 shl (retryCount - 1))

                Log.d(
                    "SSEClient",
                    "Exception: ${e.localizedMessage}. Retry $retryCount/$maxRetries"
                )

                if (retryCount >= maxRetries) {
                    Log.e("SSEClient", "Max retries reached. Stopping SSE")
                    _events.value = "__SSE_FAILURE__"
                    break
                }

                delay(delayTime)
            }
        }
    }

    // -----------------------------
    // java HttpsURLConnection SSE (kept as-is, cleaned a bit)
    // -----------------------------
    private var isListening = false
    private var reconnectJob: Job? = null

    fun startListening() {
        Log.d("SSEClient", "--startListening-- $sseUrl")
        if (isListening) return

        isListening = true
        reconnectJob = scope.launch(Dispatchers.IO) {
            var attempt = 0

            while (isListening) {
                try {
                    val accessToken = TokenManager.read("accessToken")
                    val token = accessToken?.let { "Bearer $it" }

                    if (token.isNullOrBlank()) {
                        Log.d("SSEClient", "No token found. Cannot connect.")
                        break
                    }

                    val connection = URL(sseUrl).openConnection() as HttpsURLConnection
                    connection.requestMethod = "GET"
                    connection.setRequestProperty("Accept", "text/event-stream")
                    connection.setRequestProperty("Authorization", token)
                    connection.connect()

                    val responseCode = connection.responseCode
                    if (responseCode == 401) {
                        Log.d("SSEClient", "--401--")
                        val refreshed = ApiRepository.handleAuthGetAccessTokenApi()
                        connection.disconnect()

                        if (refreshed) {
                            attempt = 0
                            continue
                        } else {
                            stopListening()
                            break
                        }
                    } else if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("SSEClient", "---Starts stream---")
                        val reader = BufferedReader(InputStreamReader(connection.inputStream))
                        attempt = 0

                        while (isListening) {
                            val line = reader.readLine() ?: break
                            if (line.startsWith("data:")) {
                                _events.value = line.removePrefix("data:").trim()
                            }
                        }

                        reader.close()
                        connection.disconnect()
                    } else {
                        Log.d("SSEClient", "---Unknown response--- $responseCode")
                        connection.disconnect()
                        handleReconnection(attempt++)
                    }
                } catch (e: EOFException) {
                    Log.w("SSEClient", "EOFException: ${e.message}")
                    handleReconnection(attempt++)
                } catch (e: SocketTimeoutException) {
                    Log.w("SSEClient", "SocketTimeoutException: ${e.message}")
                    handleReconnection(attempt++)
                } catch (e: Exception) {
                    Log.d("SSEClient", "Exception: ${e.message}")
                    handleReconnection(attempt++)
                }
            }
        }
    }

    private suspend fun handleReconnection(attempt: Int) {
        delay((attempt * 1000L).coerceAtMost(10000L))
    }

    fun clearEvents() {
        _events.value = ""
    }

    fun stopListening() {
        isListening = false
        reconnectJob?.cancel()
        reconnectJob = null
        clearEvents()
        Log.d("SSEClient", "stopListening canceled")
    }

    companion object {
        private var instance: SSEClient? = null

        fun getInstance(url: String, scope: CoroutineScope): SSEClient {
            val existing = instance
            if (existing != null) return existing

            val created = SSEClient(url, scope)
            instance = created
            return created
        }
    }
}