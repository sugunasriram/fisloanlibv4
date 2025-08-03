package com.github.sugunasriram.fisloanlibv4.fiscode.network.sse

import android.util.Log
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
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
import javax.net.ssl.HttpsURLConnection

class SSEClient(private val sseUrl: String, private val scope: CoroutineScope) {

    private val _events = MutableStateFlow("")
    val events: StateFlow<String> = _events

    private lateinit var client: OkHttpClient
    private var call: Call? = null

// OkHttpClient SSE

    fun start() {
        scope.launch(Dispatchers.IO) {
            runSse()
        }
    }

    fun stop() {
        call?.cancel()
        Log.d("SSEClient", "SSE Disconnected")
    }

    private suspend fun runSse() {
        while (scope.isActive) {
            val accessToken = TokenManager.read("accessToken")
            val bearerToken = "Bearer $accessToken"
            Log.d("SSEClient", "BearerToken : $bearerToken")
            if (bearerToken.isNullOrEmpty()) {
                Log.d("SSEClient", "No token found. Cannot connect.")
                break
            }

            client = OkHttpClient.Builder()
                .callTimeout(0, java.util.concurrent.TimeUnit.SECONDS) // no timeout
                .readTimeout(0, java.util.concurrent.TimeUnit.SECONDS)  // avoid read timeout
                .connectTimeout(0, java.util.concurrent.TimeUnit.SECONDS) // connection must be established in 10s
                .readTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build()

            val request = Request.Builder().url(sseUrl).addHeader("Authorization", bearerToken)
                .addHeader("Accept", "text/event-stream").build()



            call = client.newCall(request)

            try {
                val response = call!!.execute()
                if (!response.isSuccessful) {
                    if (response.code == 401) {
                        Log.d("SSE", "Got 401. Checking token...")
                        if (ApiRepository.handleAuthGetAccessTokenApi()) {

                            Log.d("SSEClient", "Token still exists. Restarting SSE...")
                            continue
                        } else {
                            Log.d("SSEClient", "Token expired. Cannot reconnect.")
                            break
                        }
                    } else {
                        Log.d("SSEClient", "SSE connection failed: ${response.code}")
                        break
                    }
                } else {
                    Log.d("SSEClient", "Connected to SSE")
                    response.body?.charStream()?.use { reader ->
                        val bufferedReader = BufferedReader(reader)
                        var line: String?
                        while (scope.isActive) {
                            line = bufferedReader.readLine()
                            if (line == null) {
                                Log.d("SSE", "Stream ended. Restarting...")
                                break
                            }
                            if (line.startsWith("data:")) {
                                val eventData = line.removePrefix("data:").trim()
                                _events.value = eventData
                                Log.wtf("SSEClient", "data : $eventData")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                when (e.message) {
                    "Socket closed" -> {
                        Log.d("SOCKET CLOSED", "Error: ${e.localizedMessage}")
                    }

                    else -> {
                        Log.d("SSEClient", "Error: ${e.localizedMessage}")
                    }
                }
                break
            }
        }
    }



// java HttpsURLConnection SSE
    private var isListening = false
    private var reconnectJob: Job? = null

    fun startListening() {
        Log.d("SSEClient", "--startListening-- $sseUrl")
        if (isListening) return
        isListening = true
        reconnectJob = scope.launch(Dispatchers.IO) {
            var attempt = 0
            while (isListening) {
                Log.d("SSEClient", "--isListening--")
                try {
                    // Fetch access token and prepare the authorization header
                    val accessToken = TokenManager.read("accessToken")
                    val token = "Bearer $accessToken"

                    val connection = URL(sseUrl).openConnection() as HttpsURLConnection
                    connection.requestMethod = "GET"
                    connection.setRequestProperty("Accept", "text/event-stream")
                    connection.setRequestProperty("Authorization", token)
                    connection.connect()

                    Log.d("SSEClient", "BearerToken : $token")

                    val responseCode = connection.responseCode
                    if (responseCode == 401) {
                        Log.d("SSEClient", "--401--")
                        if (ApiRepository.handleAuthGetAccessTokenApi()) {
                            connection.disconnect()
                            stopListening()
                            val sseClient = getInstance(sseUrl, scope)
                            sseClient.startListening()
                        }
                    } else if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("SSEClient", "---Starts stream---")
                        val reader = BufferedReader(InputStreamReader(connection.inputStream))
                        attempt = 0 // Reset attempt on successful connection

                        while (isListening) {
                            val line = reader.readLine() ?: break
                            if (line.startsWith("data: ")) {
                                _events.value = line.substring(6)
                            }
                        }
                        reader.close()
                        connection.disconnect()
                    } else {
                        Log.d("SSEClient", "---Unknown---")
                    }
                } catch (e: EOFException) {
                    Log.w("SSEClient", "EOFException occurred: ${e.message}")
                    handleReconnection(attempt++)
                } catch (e: SocketTimeoutException) {
                    Log.w("SSEClient", "SocketTimeoutException occurred: ${e.message}")
                    handleReconnection(attempt++)
                } catch (e: Exception) {
                    Log.d("SSEClient", "Exception: ${e.message}")
                    e.printStackTrace()
                    handleReconnection(attempt++)
                }
            }
        }
    }

    private suspend fun handleReconnection(attempt: Int) {
        delay((attempt * 1000L).coerceAtMost(10000L)) // Exponential backoff, max 10 seconds
    }

    fun clearEvents() {
        _events.value = "" // Reset to default or empty value
    }

    fun stopListening() {
        isListening = false
        Log.d("SSEClient", "stopListening canceled")
        reconnectJob?.cancel()
        clearEvents() // Clear Old Events
    }

    companion object {
        var instance: SSEClient ? = null

        fun getInstance(url: String, scope: CoroutineScope): SSEClient {
            instance?.let {
                return instance as SSEClient
            } ?: kotlin.run {
                instance = SSEClient(url, scope)
                return instance as SSEClient
            }
        }
    }

}
