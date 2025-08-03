package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.userIssues

import kotlinx.serialization.Serializable

@Serializable
data class UserReportedIssueResponse(
    val `data`: String,
    val status: Boolean,
    val statusCode: Int
)