package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan

import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf.PfOfferResponseItem
import kotlinx.serialization.Serializable

@Serializable
data class UpdateLoanAmountBody(
    val requestAmount: String? = null,
    val requestTerm: String? = null,
    val id: String? = null,
    val offerId: String? = null,
    val loanType: String? = null
)

@Serializable
data class UpdateResponse(
    val data: UpdateResponseData? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class UpdateResponseData(
    val offerResponse: OfferResponseItem? = null,
    val id: String? = null
)

@Serializable
data class UpdateLoanAmountPfResponse(
    val data: UpdateLoanAmountPfResponseData? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class UpdateLoanAmountPfResponseData(
    val offerResponse: PfOfferResponseItem? = null,
    val id: String? = null
)
