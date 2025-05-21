package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderIssueResponse(
	@SerialName("data")
	val data: OrderIssueData? = null,

	@SerialName("status")
	val status: Boolean? = null,

	@SerialName("statusCode")
	val statusCode: Int? = null
)

@Serializable
data class OrderIssueData(
	val data: List<IssueObj>? = null,
	val requestId:String? = null
)
