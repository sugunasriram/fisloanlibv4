package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GstOtpResponse(

	@SerialName("data")
	val data: GstOtpData? = null,

	@SerialName("status")
	val status: Boolean? = null,

	@SerialName("statusCode")
	val statusCode: Int? = null
)

@Serializable
data class GstOtpData(

	@SerialName("id")
	val id: String? = null,

	@SerialName("message")
	val message: String? = null
)
