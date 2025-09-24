package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OfferResponseItem
import kotlinx.serialization.Serializable

@Serializable
data class CustomerLoanList(
    val status: Boolean? = null,
    val data: ArrayList<OfferResponseItem>? = null,
    val statusCode: Long? = null
)

@Serializable
data class CancelLoan(
    val loanType: String? = null,
    val orderId: String? = null,
    val cancelType: String? = null,
    val cancelReason: String? = null
)

@Serializable
data class CancelLoanResponse(
    val status: Boolean? = null,
    val data: CancelLoanResponseData? = null,
    val statusCode: Int? = null
)

@Serializable
data class CancelLoanResponseData(
    val status: Boolean? = null,
    val data: String? = null,
    val statusCode: Int? = null
)