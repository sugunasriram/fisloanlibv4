package com.github.sugunasriram.fisloanlibv4.fis_code.network.sse

import android.util.Log
import com.github.sugunasriram.fisloanlibv4.fis_code.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.storage.TokenManager
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
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.EOFException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class SSEClient(private val url: String, private val scope: CoroutineScope) {

    private val client = HttpClient(Android) {
        install(HttpTimeout) {
            requestTimeoutMillis = 60000L
            connectTimeoutMillis = 60000L
            socketTimeoutMillis = 60000L
        }

        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            }
            serializer = KotlinxSerializer(json)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }

    private val _events = MutableStateFlow<String>("")
    val events: StateFlow<String> = _events

    private var isListening = false
    private var reconnectJob: Job? = null

    fun startListening() {
        if (isListening) return
        isListening = true
        reconnectJob = scope.launch(Dispatchers.IO) {
            var attempt = 0
            while (isListening) {
                try {
                    // Fetch access token and prepare the authorization header
                    val accessToken = TokenManager.read("accessToken")
                    val token = "Bearer $accessToken"

                    val connection = URL(url).openConnection() as HttpsURLConnection
                    connection.requestMethod = "GET"
                    connection.setRequestProperty("Accept", "text/event-stream")
                    connection.setRequestProperty("Authorization", token)
                    connection.connect()

                    Log.d("SSELOGS", "BearerToken : $token")

                    val responseCode = connection.responseCode
                    if (responseCode == 401) {
                        if (ApiRepository.handleAuthGetAccessTokenApi()) {
                            connection.disconnect()
                            stopListening()
                            startListening()
                        }
                    } else if (responseCode == HttpURLConnection.HTTP_OK) {
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
                    }
                } catch (e: EOFException) {
                    Log.w("SSEClient", "EOFException occurred: ${e.message}")
                    handleReconnection(attempt++)
                } catch (e: SocketTimeoutException) {
                    Log.w("SSEClient", "SocketTimeoutException occurred: ${e.message}")
                    handleReconnection(attempt++)
                } catch (e: Exception) {
                    e.printStackTrace()
                    handleReconnection(attempt++)
                }
            }
        }
    }

    private suspend fun handleReconnection(attempt: Int) {
        delay((attempt * 1000L).coerceAtMost(10000L)) // Exponential backoff, max 10 seconds
    }

    // Sugu
    fun clearEvents() {
        _events.value = "" // Reset to default or empty value
    }

    fun stopListening() {
        isListening = false
        reconnectJob?.cancel()
        clearEvents() // Clear Old Events
    }

    companion object {
        private lateinit var instance: SSEClient

        fun getInstance(url: String, scope: CoroutineScope): SSEClient {
            if (this::instance.isInitialized) {
                return instance
            } else {
                instance = SSEClient(url, scope)
                return instance
            }
        }
    }
}
