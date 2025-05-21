package com.github.sugunasriram.fisloanlibv4.fis_code.utils

import kotlinx.serialization.Serializable

@Serializable
data class KycUrlData (
    val id : String,
    val url: String,
    val transactionId : String
)