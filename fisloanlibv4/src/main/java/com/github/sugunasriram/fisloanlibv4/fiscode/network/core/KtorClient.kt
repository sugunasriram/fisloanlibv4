package com.github.sugunasriram.fisloanlibv4.fiscode.network.core

import android.util.Log
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods.Companion.BASE_URL
import com.github.sugunasriram.fisloanlibv4.fiscode.FsApp
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.http.takeFrom

open class KtorClient {
    private val baseUrl = Url("${BASE_URL}${ApiPaths().baseUrl}")

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
                            !info.startsWith("content-type") &&
                            !info.startsWith("date") &&
                            !info.startsWith("expires") &&
                            !info.startsWith("keep-alive") &&
                            !info.startsWith("pragma") &&
                            !info.startsWith("transfer-encoding") &&
                            !info.startsWith("x-") &&
                            !info.startsWith("BODY ") &&
                            !info.startsWith("Accept") &&
                            !info.startsWith("Content-Length") &&
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

            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
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
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)

                if (isBaseUrl) {
                    url.takeFrom(
                        URLBuilder().takeFrom(baseUrl).apply {
                            encodedPath += url.encodedPath
                        }
                    )
                }
                val requestPath = url.encodedPath
                val skipAuth = requestPath.contains("verifySession", ignoreCase = true)

                if (!skipAuth) {
                    FsApp.getInstance().token?.let { token ->
                        header("Authorization", getUserToken(token))
                        Log.d("Ktor ==>", "Adding Authorization for $requestPath")

                    }
                } else{
                    Log.d("Ktor ==>", "skipping Authorization for $requestPath")
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
