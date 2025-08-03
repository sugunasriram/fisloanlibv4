package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.userIssues

import kotlinx.serialization.Serializable

@Serializable
data class AllReportedUserIssuesResponse(
    val `data`: List<Data>,
    val status: Boolean,
    val statusCode: Int
)

@Serializable
data class Data(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val issue_id: String,
    val message: String,
    val status: String,
    val updatedAt: String,
    val user_id: String
)