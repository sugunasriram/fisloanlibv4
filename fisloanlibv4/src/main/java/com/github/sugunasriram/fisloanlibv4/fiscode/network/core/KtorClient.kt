package com.github.sugunasriram.fisloanlibv4.fiscode.network.core

import android.util.Log
import com.github.sugunasriram.fisloanlibv4.fiscode.FsApp
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

open class KtorClient {
//    private val baseUrl = Url("${BASE_URL}${ApiPaths().baseUrl}")
    private val baseUrl =" $BASE_URL${ApiPaths().baseUrl}"

    companion object {
        private lateinit var instance: KtorClient
        fun getInstance(isBaseUrl: Boolean = true): HttpClient {
            return if (this::instance.isInitialized) {
                instance.getClient(isBaseUrl)
            } else {
                instance = KtorClient()
                instance.getClient(isBaseUrl)
            }
        }
    }

    fun getClient(isBaseUrl: Boolean = true): HttpClient {
        return HttpClient(Android) {

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        val info = message.replace("-> ", "")
                        if (
                            !info.startsWith("access-control") &&
                            !info.startsWith("cache-control") &&
                            !info.startsWith("connection") &&
//                            !info.startsWith("content-type") &&
                            !info.startsWith("date") &&
                            !info.startsWith("expires") &&
//                            !info.startsWith("keep-alive") &&
                            !info.startsWith("pragma") &&
//                            !info.startsWith("transfer-encoding") &&
//                            !info.startsWith("x-") &&
                            !info.startsWith("BODY ") &&
                            !info.startsWith("Accept") &&
//                            !info.startsWith("Content-Length") &&
                            info.isNotEmpty()
                        ) {
                            Log.wtf("Ktor ==>", message)
//                            if (BuildConfig.DEBUG) {
//                                    FileLogger.writeToFile(message, true)
//                            }
                        }
                    }
                }
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
//                serializer = KotlinxSerializer(
                json(
                   Json {
                        prettyPrint = true
                        isLenient = true
                        encodeDefaults = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 300000L
                connectTimeoutMillis = 300000L
                socketTimeoutMillis = 300000L
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("Ktor <==", "${response.status.value}")
                }
            }

            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)

                FsApp.getInstance().token?.let { token ->
                    header("Authorization", getUserToken(token))
                }
            }
        }
    }
    private fun getUserToken(token: String): String {
        return if (token.isNotEmpty() && !token.startsWith("Bearer ")) {
            "Bearer $token"
        } else {
            token
        }
    }
}
