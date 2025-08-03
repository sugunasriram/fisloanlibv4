package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan

import kotlinx.serialization.Serializable

@Serializable
data class UpdateConsentHandler(
    val data: UpdatedObject? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class UpdateConsentHandlerBody(
    val subType: String? = null,
    val id: String? = null,
    val amount: String? = null,
    val consentStatus: String? = null,
    val loanType: String? = null
)
