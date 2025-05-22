package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm

import kotlinx.serialization.Serializable

@Serializable
data class IssueCategories(
    val data: List<CategorieItem?>? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class CategorieItem(
    val name: String? = null,
    val id: String? = null
)
