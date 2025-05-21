package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan

import kotlinx.serialization.Serializable

@Serializable
data class UpdateLoanAgreement(
	val data: UpdatedObject? = null,
	val status: Boolean? = null,
	val statusCode: Int? = null
)

@Serializable
data class UpdateLoanBody(
	val loanType: String? = null,
	val subType: String? = null,
	val id: String? = null,
	val amount: String? = null
)

@Serializable
data class UpdatedObject(
	val id: String? = null,
	val updatedObject: OfferResponseItem? = null
)
