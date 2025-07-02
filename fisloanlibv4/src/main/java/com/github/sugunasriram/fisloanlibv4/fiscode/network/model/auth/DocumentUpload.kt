package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class DocumentUpload(
    val status: Int? = null,
    val message: String? = null,
    val data: List<String?>? = null
)

@Serializable
data class Document(
    val mimetype: String? = null,
    val base64: String? = null,
    val url : String? = null
)
