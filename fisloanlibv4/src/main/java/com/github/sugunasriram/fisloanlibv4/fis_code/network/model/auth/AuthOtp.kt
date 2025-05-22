package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthOtp(
    val data: Token? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class GenerateAuthOtp(
    val status: Boolean? = false,
    val data: Data? = null,
    val statusCode: Int? = 0
)
