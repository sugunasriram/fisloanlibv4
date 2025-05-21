package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm

import kotlinx.serialization.Serializable

@Serializable
data class CloseIssueBody (
    val loanType: String? = null,

    val issueId: String? = null,
    val status: String? = null,
    val rating: String? = null
)

@Serializable
data class CloseIssueResponse (
    val status: Boolean? = null,
    val data: CloseIssueResponseData? = null,
    val statusCode: Long? = null
)

@Serializable
data class CloseIssueResponseData (
    val requestId: String? = null,
    val message: String? = null
)
