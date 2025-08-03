package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.igm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueByIdResponse(

    @SerialName("data")
    val data: Data? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class IssueAdditionalDescription(

    @SerialName("content_type")
    val contentType: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Serializable
data class Issue(

    @SerialName("issue_actions")
    val issueActions: IssueActions? = null,

    @SerialName("resolution_provider")
    val resolutionProvider: ResolutionProvider? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("resolution")
    val resolution: Resolution? = null,

    @SerialName("complainant_info")
    val complainantInfo: ComplainantInfo? = null,

    @SerialName("expected_response_time")
    val expectedResponseTime: IssueExpectedResponseTime? = null,

    @SerialName("sub_category")
    val subCategory: String? = null,

    @SerialName("issue_type")
    val issueType: String? = null,

    @SerialName("rating")
    val rating: String? = null,

    @SerialName("description")
    val description: Description? = null,

    @SerialName("source")
    val source: Source? = null,

    @SerialName("order_details")
    val orderDetails: OrderDetails? = null,

    @SerialName("expected_resolution_time")
    val expectedResolutionTime: IssueExpectedResolutionTime? = null,

    @SerialName("category")
    val category: String? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("additional_info_required")
    val additionalInfoRequired: String? = null
)

@Serializable
data class Context(

    @SerialName("transaction_id")
    val transactionId: String? = null,

    @SerialName("bpp_id")
    val bppId: String? = null,

    @SerialName("bpp_uri")
    val bppUri: String? = null,

    @SerialName("domain")
    val domain: String? = null,

    @SerialName("action")
    val action: String? = null,

    @SerialName("location")
    val location: Location? = null,

    @SerialName("message_id")
    val messageId: String? = null,

    @SerialName("ttl")
    val ttl: String? = null,

    @SerialName("version")
    val version: String? = null,

    @SerialName("bap_uri")
    val bapUri: String? = null,

    @SerialName("bap_id")
    val bapId: String? = null,

    @SerialName("timestamp")
    val timestamp: String? = null
)

@Serializable
data class IssueClose(

    @SerialName("context")
    val context: Context? = null,

    @SerialName("message")
    val message: Message? = null
)

@Serializable
data class IssueOpen(

    @SerialName("context")
    val context: Context? = null,

    @SerialName("message")
    val message: Message? = null
)

@Serializable
data class Resolution(

    @SerialName("action_triggered")
    val actionTriggered: String? = null,

    @SerialName("short_desc")
    val shortDesc: String? = null,

    @SerialName("long_desc")
    val longDesc: String? = null
)

@Serializable
data class Message(

    @SerialName("issue")
    val issue: Issue? = null
)

@Serializable
data class IssueActions(

    @SerialName("respondent_actions")
    val respondentActions: List<RespondentActionsItem?>? = null,

    @SerialName("complainant_actions")
    val complainantActions: List<ComplainantActionsItem?>? = null
)

@Serializable
data class Location(

    @SerialName("country")
    val country: Country? = null,

    @SerialName("city")
    val city: City? = null
)

@Serializable
data class ComplainantInfo(

    @SerialName("person")
    val person: IssuePerson? = null,

    @SerialName("contact")
    val contact: IssueContact? = null
)

@Serializable
data class OnIssueStatus(

    @SerialName("context")
    val context: Context? = null,

    @SerialName("message")
    val message: Message? = null
)

@Serializable
data class OnIssue(

    @SerialName("context")
    val context: Context? = null,

    @SerialName("message")
    val message: Message? = null
)

@Serializable
data class RespondentInfo(

    @SerialName("organization")
    val organization: Organization? = null,

    @SerialName("resolution_support")
    val resolutionSupport: ResolutionSupport? = null,

    @SerialName("type")
    val type: String? = null
)

@Serializable
data class IssueUpdatedBy(

    @SerialName("contact_phone")
    val contactPhone: String? = null,

    @SerialName("org")
    val org: IssueOrg? = null,

    @SerialName("org_name")
    val orgName: String? = null,

    @SerialName("contact_email")
    val contactEmail: String? = null,

    @SerialName("person")
    val person: IssuePerson? = null,

    @SerialName("contact")
    val contact: IssueContact? = null
)

@Serializable
data class IssueResolutionsProviders(

    @SerialName("contact_phone")
    val contactPhone: String? = null,

    @SerialName("contact_person")
    val contactPerson: String? = null,

    @SerialName("organization_name")
    val organizationName: String? = null,

    @SerialName("contact_email")
    val contactEmail: String? = null
)

@Serializable
data class Organization(

    @SerialName("org")
    val org: IssueOrg? = null,

    @SerialName("person")
    val person: IssuePerson? = null,

    @SerialName("contact")
    val contact: IssueContact? = null
)

@Serializable
data class AdditionalDesc(

    @SerialName("content_type")
    val contentType: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Serializable
data class IssuePerson(

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class Country(

    @SerialName("code")
    val code: String? = null
)

@Serializable
data class Data(

    @SerialName("data")
    val data: Data? = null,

    @SerialName("requestId")
    val requestId: String? = null,

    @SerialName("summary")
    val summary: IssueSummary? = null,

    @SerialName("createdAt")
    val createdAt: String? = null,

    @SerialName("__v")
    val v: Int? = null,

    @SerialName("details")
    val details: Details? = null,

    @SerialName("_id")
    val id: String? = null,

    @SerialName("updatedAt")
    val updatedAt: String? = null
)

@Serializable
data class IssueContact(

    @SerialName("phone")
    val phone: String? = null,

    @SerialName("email")
    val email: String? = null
)

@Serializable
data class ResolutionProvider(

    @SerialName("respondent_info")
    val respondentInfo: RespondentInfo? = null
)

@Serializable
data class Details(

    @SerialName("on_issue")
    val onIssue: OnIssue? = null,

    @SerialName("issue_open")
    val issueOpen: IssueOpen? = null,

    @SerialName("issue_close")
    val issueClose: IssueClose? = null,

    @SerialName("on_issue_status")
    val onIssueStatus: OnIssueStatus? = null,

    @SerialName("_id")
    val id: String? = null
)

@Serializable
data class ComplainantActionsItem(

    @SerialName("complainant_action")
    val complainantAction: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("updated_by")
    val updatedBy: IssueUpdatedBy? = null,

    @SerialName("short_desc")
    val shortDesc: String? = null
)

@Serializable
data class IssueExpectedResolutionTime(

    @SerialName("duration")
    val duration: String? = null
)

@Serializable
data class OrderDetails(

    @SerialName("provider_id")
    val providerId: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("state")
    val state: String? = null
)

@Serializable
data class Source(

    @SerialName("network_participant_id")
    val networkParticipantId: String? = null,

    @SerialName("type")
    val type: String? = null
)

@Serializable
data class IssueExpectedResponseTime(

    @SerialName("duration")
    val duration: String? = null
)

@Serializable
data class GrosItem(

    @SerialName("person")
    val person: IssuePerson? = null,

    @SerialName("contact")
    val contact: IssueContact? = null,

    @SerialName("gro_type")
    val groType: String? = null
)

@Serializable
data class IssueOrg(

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class RespondentActionsItem(

    @SerialName("respondent_action")
    val respondentAction: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("updated_by")
    val updatedBy: IssueUpdatedBy? = null,

    @SerialName("short_desc")
    val shortDesc: String? = null,

    @SerialName("cascaded_level")
    val cascadedLevel: Int? = null
)

@Serializable
data class City(

    @SerialName("code")
    val code: String? = null
)

@Serializable
data class IssueSummary(

    @SerialName("additional_description")
    val additionalDescription: IssueAdditionalDescription? = null,

    @SerialName("short_description")
    val shortDescription: String? = null,

    @SerialName("complainant_actions")
    val complainantActions: List<ComplainantActionsItem?>? = null,

    @SerialName("images")
    val images: List<String?>? = null,

    @SerialName("sub_category_desc")
    val subCategoryDesc: String? = null,

    @SerialName("respondent_actions")
    val respondentActions: List<RespondentActionsItem?>? = null,

    @SerialName("expected_response_time")
    val expectedResponseTime: IssueExpectedResponseTime? = null,

    @SerialName("loanType")
    val loanType: String? = null,

    @SerialName("sub_category")
    val subCategory: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("resolutions")
    val resolutions: IssueResolutions? = null,

    @SerialName("long_description")
    val longDescription: String? = null,

    @SerialName("expected_resolution_time")
    val expectedResolutionTime: IssueExpectedResolutionTime? = null,

    @SerialName("resolutions_providers")
    val resolutionsProviders: IssueResolutionsProviders? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("provider_id")
    val providerId: String? = null,

    @SerialName("_id")
    val id: String? = null,

    @SerialName("state")
    val state: String? = null,

    @SerialName("category")
    val category: String? = null,

    @SerialName("provider_name")
    val providerName: String? = null,

    @SerialName("order_id")
    val orderId: String? = null,

    @SerialName("status")
    val status: String? = null
)

@Serializable
data class ResolutionSupport(

    @SerialName("contact")
    val contact: IssueContact? = null,

    @SerialName("chat_link")
    val chatLink: String? = null,

    @SerialName("gros")
    val gros: List<GrosItem?>? = null
)

@Serializable
data class IssueResolutions(

    @SerialName("action_triggered")
    val actionTriggered: String? = null,

    @SerialName("short_desc")
    val shortDesc: String? = null,

    @SerialName("long_desc")
    val longDesc: String? = null
)

@Serializable
data class Description(

    @SerialName("images")
    val images: List<String?>? = null,

    @SerialName("additional_desc")
    val additionalDesc: AdditionalDesc? = null,

    @SerialName("short_desc")
    val shortDesc: String? = null,

    @SerialName("long_desc")
    val longDesc: String? = null
)
