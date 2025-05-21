package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateIssueResponse (
    val status: Boolean? = null,
    val data: CreateIssueResponseData? = null,
    val statusCode: Long? = null
)

@Serializable
data class CreateIssueResponseData (
    val requestId: String? = null,
    val issue_id: String? = null,
    val message: String? = null
)

@Serializable
data class CreateIssueBody (
    val loanType: String? = null,

    @SerialName("order_id")
    val orderID: String? = null,

    @SerialName("order_state")
    val orderState: String? = null,

    @SerialName("provider_id")
    val providerID: String? = null,

    @SerialName("issue_category")
    val issueCategory: String? = null,

    @SerialName("issue_sub_category")
    val issueSubCategory: String? = null,

    @SerialName("issue_short_disc")
    val issueShortDisc: String? = null,

    @SerialName("issue_long_disc")
    val issueLongDisc: String? = null,

    @SerialName("discription_url")
    val discriptionURL: String? = null,

    @SerialName("discription_content_type")
    val discriptionContentType: String? = null,

    @SerialName("issue_type")
    val issueType: String? = null,

    val status: String? = null,

    @SerialName("description_images")
    val descriptionImages: List<String>? = null
)

@Serializable
data class IssueListBody (
    val page: Int? = 1,
    val pageSize: Int? = 10
)

@Serializable
data class IssueList (
    val issueList: List<IssueObj>? = null
)

@Serializable
data class IssueObj(
    @SerialName("_id") val id: String? = null,
    @SerialName("summary") val summary: Summary? = null,
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("updatedAt") val updatedAt: String? = null,
)

@Serializable
data class Summary(
    @SerialName("_id") val id: String? = null,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("order_id") val orderId: String? = null,
    @SerialName("short_description") val shortDescription: String? = null,
    @SerialName("long_description") val longDescription: String? = null,
    @SerialName("additional_description") val additionalDescription: AdditionalDescription? = null,
    @SerialName("images") val images: List<String>? = null,
    @SerialName("category") val category: String ?=null,
    @SerialName("sub_category_desc") val subCategoryDesc: String? = null,
    @SerialName("state") val state: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("provider_id") val providerId: String? = null,
    @SerialName("provider_name") val providerName: String? = null,
    @SerialName("respondent_actions") val respondentActions: List<RespondentAction>? = null,
    @SerialName("complainant_actions") val complainantActions: List<ComplainantAction>? = null,
    @SerialName("resolutions") val resolutions: Resolutions ? = null,
    @SerialName("resolutions_providers") val resolutionsProviders: ResolutionsProviders ? = null,
    @SerialName("expected_response_time") val expectedResponseTime: ExpectedResponseTime? = null,
    @SerialName("expected_resolution_time") val expectedResolutionTime: ExpectedResolutionTime? = null,
    @SerialName("loanType") val loanType: String ?= null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("sub_category") val subCategory: String? = null
)

@Serializable
data class AdditionalDescription(
    @SerialName("url") val url: String? = null,
    @SerialName("content_type") val contentType: String? = null
)

@Serializable
data class RespondentAction(
    @SerialName("respondent_action") val respondentAction: String? = null,
    @SerialName("short_desc") val shortDesc: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("updated_by") val updatedBy: UpdatedBy? = null,
    @SerialName("cascaded_level") val cascadedLevel: Int? = null,
)

@Serializable
data class ComplainantAction(
    @SerialName("complainant_action") val complainantAction: String? = null,
    @SerialName("short_desc") val shortDesc: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("updated_by") val updatedBy: ComplainantUpdatedBy? = null
)

@Serializable
data class Resolutions(
    @SerialName("short_desc") val shortDesc: String? = null,
    @SerialName("long_desc") val longDesc: String? = null,
    @SerialName("action_triggered") val actionTriggered: String? = null
)

@Serializable
data class ResolutionsProviders(
    @SerialName("contact_phone") val contactPhone: String? = null,
    @SerialName("contact_email") val contactEmail: String? = null,
    @SerialName("contact_person") val contactPerson: String? = null,
    @SerialName("organization_name") val organizationName: String? = null
)

@Serializable
data class ExpectedResponseTime(
    @SerialName("duration") val duration: String? = null
)

@Serializable
data class ExpectedResolutionTime(
    @SerialName("duration") val duration: String? = null
)

@Serializable
data class UpdatedBy(
    @SerialName("org") val org: Org? = null,
    @SerialName("contact") val contact: Contact? = null,
    @SerialName("person") val person: Person? = null,
    @SerialName("contact_phone") val contactPhone: String? = null,
    @SerialName("org_name") val orgName: String? = null,
    @SerialName("contact_email") val contactEmail: String? = null,
)

@Serializable
data class ComplainantUpdatedBy(
    @SerialName("org_name") val orgName: String? = null,
    @SerialName("contact_phone") val contactPhone: String? = null,
    @SerialName("contact_email") val contactEmail: String? = null,
    @SerialName("org") val org: Org? = null
)

@Serializable
data class Org(
    @SerialName("name") val name: String? = null
)

@Serializable
data class Contact(
    @SerialName("phone") val phone: String? = null,
    @SerialName("email") val email: String? = null
)

@Serializable
data class Person(
    @SerialName("name") val name: String? = null
)


@Serializable
data class IssueListResponse(
    @SerialName("status") val status: Boolean? = false,
    @SerialName("data") val data: ResponseData? = null,
    @SerialName("statusCode") val statusCode: Int?= null
)

@Serializable
data class ResponseData(
    @SerialName("requestId") val requestId: String? = null,
    @SerialName("data") val pageData: PageData? = null
)

@Serializable
data class PageData(
    @SerialName("page") val page: Int? = null,
    @SerialName("pageSize") val pageSize: Int? = null,
    @SerialName("total_issues") val totalIssues: Int? = null,
    @SerialName("total_page") val totalPage: Int? = null,
    @SerialName("data") val issues: List<IssueObj>? = null
)

