package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LogIn(
    val orderId: String? = null,
    val message: String? = null
// 	val status: Boolean? = null,
// 	val statusCode: Int? = null
)

@Serializable
data class Token(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val securityKey: String? = null,
    val sseId: String? = null
)

@Serializable
data class LoginDetails(
    val orderId: String? = null,
    val otp: String? = null,
    val deviceInfo: DeviceInfo? = null // PreProd

)

@Serializable
data class DeviceInfo(
    val brand: String,
    val deviceName: String,
    val deviceType: String,
    val isDevice: String,
    val manufacturer: String,
    val modelId: String,
    val modelName: String,
    val osName: String,
    val androidId: String,
    val osVersion: String,
    val platformApiLevel: String
)
