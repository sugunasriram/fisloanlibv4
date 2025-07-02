package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan

import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Descriptor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoanAgreement(
    val data: LoanAgreementResponse? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class LoanAgreementResponse(
    val id: String? = null,
    @SerialName("loanAgreementObject")
    val loanAgreementObject: OfferResponseItem? = null
)
@Serializable
data class ConfirmLoanAgreement(
    val data: ConfirmLoanAgreementData? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)
@Serializable
data class ConfirmLoanAgreementData(
    val id: String? = null,
    val aaURLObject: OfferResponseItem? = null,
    val transactionId: String? = null,
    val url: String? = null
)

@Serializable
data class ListItem(
    val descriptor: Descriptor? = null,
    val value: String? = null
)
@Serializable
data class DocumentsItem(
    val mimeType: String? = null,
    val descriptor: Descriptor? = null,
    val url: String? = null
)