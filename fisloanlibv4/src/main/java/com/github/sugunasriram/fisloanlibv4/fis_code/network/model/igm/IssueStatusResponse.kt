package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm

import kotlinx.serialization.Serializable

@Serializable
data class IssueStatusResponse(
    val status: Boolean? = null,
    val data: IssueData? = null,
    val statusCode: Long? = null
)

@Serializable
data class IssueData(
    val requestId: String? = null,
    val message: String? = null
)
