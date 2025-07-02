package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckOrderIssueModel(
    @SerialName("data") val data: CheckOrderData? = null,
    @SerialName("status") val status: Boolean? = null,
    @SerialName("statusCode") val statusCode: Int? = null
)

@Serializable
data class CheckOrderData(
    @SerialName("data") val data: List<CheckItem?>? = null,
    @SerialName("requestId") val requestId: String? = null
)

@Serializable
data class CheckItem(
    @SerialName("summary") val summary: Summary? = null,
    @SerialName("_id") val id: String? = null
)
