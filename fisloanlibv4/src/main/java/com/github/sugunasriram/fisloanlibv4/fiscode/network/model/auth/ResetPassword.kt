package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResetPassword(
	val message: String? = null,
	val status: Boolean? = null,
	val statusCode: Int? = null
)

