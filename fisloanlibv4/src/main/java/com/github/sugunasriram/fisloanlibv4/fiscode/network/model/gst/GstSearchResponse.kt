package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst

import kotlinx.serialization.Serializable

@Serializable
data class GstSearchResponse(
	val data: GstSearchData? = null,
	val status: Boolean? = null,
	val statusCode: Int? = null
)

@Serializable
data class GstSearchData(
	val id: String? = null,
	val url: String? = null,
	val transactionId: String? = null
)

