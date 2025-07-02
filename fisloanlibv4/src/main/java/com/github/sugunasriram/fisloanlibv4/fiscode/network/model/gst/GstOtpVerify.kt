package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GstOtpVerify(

	@SerialName("data")
	val data: OtpVerify? = null,

	@SerialName("status")
	val status: Boolean? = null,

	@SerialName("statusCode")
	val statusCode: Int? = null
)

@Serializable
data class OtpVerify(

	@SerialName("id")
	val id: String? = null,

	@SerialName("message")
	val message: String? = null
)
