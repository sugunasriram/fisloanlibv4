package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankList(
    val data: ArrayList<BankItem?>? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class BankItem(
    @SerialName("bank_name")
    val bankName: String? = null,
    @SerialName("image_bank")
    val imageBank: String? = null,
    @SerialName("_id")
    val id: String? = null
)
