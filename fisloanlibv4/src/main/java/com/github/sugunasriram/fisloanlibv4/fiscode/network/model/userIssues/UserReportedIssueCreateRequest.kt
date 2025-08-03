package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.userIssues

import kotlinx.serialization.Serializable

@Serializable
data class UserReportedIssueCreateRequest(
    val email: String,
    val message: String,
    val mobileNumber: String,
    val status: String
)