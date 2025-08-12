package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.ListItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.Tags
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionResponse(
	val statusCode: Int,
	val status: Boolean,
	val message: String,
	val data: CreateSessionData
) : java.io.Serializable


@Serializable
data class CreateSessionData(
	val id: String
) : java.io.Serializable


@Serializable
data class CreateSessionRequest(
	val type: String? = null,
	val statusCode: Int,
	val status: Boolean? = null,

	val data: CreateSessionRequestData? = null,
	val error: ErrorOject? = null

): java.io.Serializable

@Serializable
data class CreateSessionRequestData(
	val downPaymentAmount: String? = null,
	val loanId: String? = null,
) : java.io.Serializable

@Serializable
data class ErrorOject(
	val message: String? = null
) : java.io.Serializable