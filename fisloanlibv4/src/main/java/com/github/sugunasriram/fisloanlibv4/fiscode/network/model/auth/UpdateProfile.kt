package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfile(
	val data: Profile? = null,
	val status: Boolean? = null,
	val statusCode: Int? = null
)
@Serializable
data class UpdateData(
	val upsertedId: String? = null,
	val upsertedCount: Int? = null,
	val acknowledged: Boolean? = null,
	val modifiedCount: Int? = null,
	val matchedCount: Int? = null
)

