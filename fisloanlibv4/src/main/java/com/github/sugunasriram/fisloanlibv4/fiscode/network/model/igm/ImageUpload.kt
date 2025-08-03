package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm

import kotlinx.serialization.Serializable

@Serializable
data class ImageUpload(
    val status: Int? = null,
    val message: String? = null,
    val data: List<String>? = null
)

@Serializable
data class ImageUploadBody(
    val images: List<Images>? = null
)

@Serializable
data class Images(
    val mimetype: String? = null,
    val base64: String? = null
)
