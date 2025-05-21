package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderPaymentStatusResponse(
    val status: Boolean? = false,
    val data: ArrayList<OrderPaymentStatusItem>? = null,
    val statusCode: Long? = null
)


@Serializable
data class OrderPaymentStatusItem(
    @SerialName("_id")
    val id: String? = null,
    @SerialName("__v")
    val version: Int? = null,

    @SerialName("collected_by")
    val collected_by: String? = null,
    val createdAt: String? = null,
    val order_id: String? = null,
    @SerialName("params")
    val params: Params? = null,
    val status: String? = null,
    @SerialName("tags")
    val tags: List<TagItem>? = null,
    @SerialName("time")
    val time: TimeItem? = null,
    @SerialName("type")
    val type: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class TimeItem(
    val label: String? = null,
    val range: Range? = null
)

@Serializable
data class TagItem(
    val descriptor: Descriptor? = null,
    val display: Boolean? = false,
    val list: List<TagListItem>? = null
)

@Serializable
data class TagListItem(
    val descriptor: Descriptor? = null,
    val value: String? = null
)































