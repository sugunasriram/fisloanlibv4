package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchModel(
    val data: SearchResponseModel? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class SearchBodyModel(
    val loanType: String? = null,
    val endUse: String? = null,
    val bureauConsent: String? = null
)

@Serializable
data class SearchResponseModel(
    val id: String? = null,
    val url: String? = null,
    val transactionId: String? = null,
    val offers: Boolean? = null,
    val offerResponse: List<Offer?>? = null,
    val consentResponse: List<AAConsentDetails?>? = null,
    val rejectedLenders: List<RejectedLenders?>? = null
)

@Serializable
data class RejectedLenders(
    val name: String? = null,
    val image: String? = null,
    val reason: String? = null,
    @SerialName("min_interest_rate")
    val minInterestRate: String? = null,
    @SerialName("max_interest_rate")
    val maxInterestRate: String? = null,
    @SerialName("min_loan_amount")
    val minLoanAmount: String? = null,
    @SerialName("max_loan_amount")
    val maxLoanAmount: String? = null
)

@Serializable
data class AAConsentDetails(
    val name: String? = null,
    val image: String? = null,
    val min_interest_rate: String? = null,
    val max_interest_rate: String? = null
)

@Serializable
data class GetLenderStatusModel(
    val data: LenderStatusResponse? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class LenderStatusResponse(
    val response: List<LenderResponseData>? = null
)

@Serializable
data class LenderResponseData(
    val name: String? = null,
    val image: String? = null,
    @SerialName("MIN_INTEREST_RATE")
    val minInterestRate: String? = null,
    @SerialName("MAX_INTEREST_RATE")
    val maxInterestRate: String? = null,
    @SerialName("MIN_TENURE")
    val minTenure: String? = null,
    @SerialName("MAX_TENURE")
    val maxTenure: String? = null,
    @SerialName("MIN_LOAN_AMOUNT")
    val minLoanAmount: String? = null,
    @SerialName("MAX_LOAN_AMOUNT")
    val maxLoanAmount: String? = null
)
