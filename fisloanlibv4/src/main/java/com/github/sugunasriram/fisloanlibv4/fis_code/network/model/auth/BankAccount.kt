package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class BankAccount(
    val data: List<DataItem?>? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class DataItem(
    @SerialName("account_type")
    val accountType: String? = null,
    @SerialName("bank_account_number")
    val bankAccountNumber: String? = null,
    @SerialName("bank_ifsc_code")
    val bankIfscCode: String? = null,
    @SerialName("account_holder_name")
    val accountHolderName: String? = null
)
