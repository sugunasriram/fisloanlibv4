package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

data class PanVerificationResponse(
    val data: NameData,
    val status: Boolean,
    val statusCode: Int
)

data class NameData(
    val first_name: String,
    val last_name: String
)