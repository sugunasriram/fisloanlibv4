package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueSubCategories(
    val data: SubCategoriesData? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class SubCategoriesItem(
    @SerialName("issue_sub_category")
    val issueSubCategory: String? = null,
    @SerialName("issue_sub_category_types")
    val issueSubCategoryTypes: List<IssueSubCategoryTypesItem?>? = null
)

@Serializable
data class SubCategoriesData(
    @SerialName("_id")
    val id: String? = null,
    val category: String? = null,
    val subCategories: List<SubCategoriesItem?>? = null
)

@Serializable
data class IssueSubCategoryTypesItem(
    val subCatCode: String? = null,
    val illustration: String? = null,
    val jsonMemberEnum: String? = null
)

