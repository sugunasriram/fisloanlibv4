package com.github.sugunasriram.fisloanlibv4.fiscode.network.model

import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.AAConsentDetails
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.Offer
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.RejectedLenders
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatus(

    @SerialName("data")
    val data: UserData? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class UserData(

    @SerialName("id")
    val id: String? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("txnId")
    val txnId: String? = null,

    @SerialName("data")
    val data: List<UserItem?>? = null
)

@Serializable
data class ItemPrice(

    @SerialName("currency")
    val currency: String? = null,

    @SerialName("value")
    val value: String? = null
)

@Serializable
data class ImagesItem(

    @SerialName("size_type")
    val sizeType: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Serializable
data class QuotePrice(

    @SerialName("currency")
    val currency: String? = null,

    @SerialName("value")
    val value: String? = null
)

@Serializable
data class UserItem(
    @SerialName("data")
    val data: List<UserStatusItem?>? = null,

    @SerialName("step")
    val step: String? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("_id")
    val id: String? = null,

    @SerialName("consentResponse")
    val consentResponse: List<AAConsentDetails?>? = null,
    @SerialName("rejectedLenders")
    val rejectedLenders: List<RejectedLenders?>? = null,

    @SerialName("offerResponse")
    val offerResponse: List<Offer?>? = null
)

@Serializable
data class UserStatusItem(
    @SerialName("_id")
    val id: String? = null,

    @SerialName("provider_tags")
    val providerTags: List<ProviderTagsItem?>? = null,

    @SerialName("bpp_id")
    val bppId: String? = null,

    @SerialName("item_id")
    val itemId: String? = null,

    @SerialName("item_tags")
    val itemTags: List<ItemTagsItem?>? = null,

    @SerialName("item_price")
    val itemPrice: ItemPrice? = null,

    @SerialName("quote_id")
    val quoteId: String? = null,

    @SerialName("form_id")
    val formId: String? = null,

    @SerialName("provider_descriptor")
    val providerDescriptor: ProviderDescriptor? = null,

    @SerialName("item_descriptor")
    val itemDescriptor: ItemDescriptor? = null,

    @SerialName("from_url")
    val fromUrl: String? = null,

    @SerialName("quote_price")
    val quotePrice: QuotePrice? = null,

    @SerialName("txn_id")
    val txnId: String? = null,

    @SerialName("expires_at")
    val expiresAt: String? = null,

    @SerialName("bpp_uri")
    val bppUri: String? = null,

    @SerialName("provider_id")
    val providerId: String? = null,

    @SerialName("quote_breakup")
    val quoteBreakup: List<QuoteBreakupItem?>? = null,

    @SerialName("settlement_amount")
    val settlementAmount: String? = null,

    @SerialName("msg_id")
    val msgId: String? = null,

    @SerialName("payments")
    val payments: List<PaymentsItem?>? = null,

    @SerialName("cancellation_terms")
    val cancellationTerms: List<CancellationTermsItem?>? = null,

    @SerialName("fulfillments")
    val fulfillments: List<FulfillmentsItem?>? = null,

    @SerialName("payment_id")
    val paymentId: String? = null
)

@Serializable
data class ProviderTagsItem(

    @SerialName("name")
    val name: String? = null,

    @SerialName("tags")
    val tags: Tags? = null
)

@Serializable
data class ItemTagsItem(

    @SerialName("display")
    val display: Boolean? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("tags")
    val tags: Tags? = null
)

@Serializable
data class ItemDescriptor(

    @SerialName("code")
    val code: String? = null,

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class Tags(

    @SerialName("LSP_NAME")
    val lSPNAME: String? = null,

    @SerialName("LSP_ADDRESS")
    val lSPADDRESS: String? = null,

    @SerialName("LSP_EMAIL")
    val lSPEMAIL: String? = null,

    @SerialName("LSP_CONTACT_NUMBER")
    val lSPCONTACTNUMBER: String? = null,

    @SerialName("GRO_CONTACT_NUMBER")
    val gROCONTACTNUMBER: String? = null,

    @SerialName("CUSTOMER_SUPPORT_LINK")
    val cUSTOMERSUPPORTLINK: String? = null,

    @SerialName("GRO_EMAIL")
    val gROEMAIL: String? = null,

    @SerialName("GRO_NAME")
    val gRONAME: String? = null,

    @SerialName("CUSTOMER_SUPPORT_CONTACT_NUMBER")
    val cUSTOMERSUPPORTCONTACTNUMBER: String? = null,

    @SerialName("GRO_DESIGNATION")
    val gRODESIGNATION: String? = null,

    @SerialName("GRO_ADDRESS")
    val gROADDRESS: String? = null,

    @SerialName("CUSTOMER_SUPPORT_EMAIL")
    val cUSTOMERSUPPORTEMAIL: String? = null,

    @SerialName("NUMBER_OF_INSTALLMENTS_OF_REPAYMENT")
    val nUMBEROFINSTALLMENTSOFREPAYMENT: String? = null,

    @SerialName("APPLICATION_FEE")
    val aPPLICATIONFEE: String? = null,

    @SerialName("INTEREST_RATE_CONVERSION_CHARGE")
    val iNTERESTRATECONVERSIONCHARGE: String? = null,

    @SerialName("COOL_OFF_PERIOD")
    val cOOLOFFPERIOD: String? = null,

    @SerialName("TERM")
    val tERM: String? = null,

    @SerialName("TNC_LINK")
    val tNCLINK: String? = null,

    @SerialName("FORECLOSURE_FEE")
    val fORECLOSUREFEE: String? = null,

    @SerialName("OTHER_PENALTY_FEE")
    val oTHERPENALTYFEE: String? = null,

    @SerialName("INTEREST_RATE")
    val iNTERESTRATE: String? = null,

    @SerialName("REPAYMENT_FREQUENCY")
    val rEPAYMENTFREQUENCY: String? = null,

    @SerialName("INSTALLMENT_AMOUNT")
    val iNSTALLMENTAMOUNT: String? = null,

    @SerialName("INTEREST_RATE_TYPE")
    val iNTERESTRATETYPE: String? = null,

    @SerialName("DELAY_PENALTY_FEE")
    val dELAYPENALTYFEE: String? = null,

    @SerialName("ANNUAL_PERCENTAGE_RATE")
    val aNNUALPERCENTAGERATE: String? = null
)

@Serializable
data class ProviderDescriptor(

    @SerialName("images")
    val images: List<ImagesItem?>? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("short_desc")
    val shortDesc: String? = null,

    @SerialName("long_desc")
    val longDesc: String? = null
)

@Serializable
data class QuoteBreakupItem(

    @SerialName("currency")
    val currency: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("value")
    val value: String? = null
)

@Serializable
data class PaymentsItem(

    @SerialName("id")
    val id: String? = null,

    @SerialName("time")
    val time: Time? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("params")
    val params: Params? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("collected_by")
    val collectedBy: String? = null,

    @SerialName("tags")
    val tags: List<TagsItem?>? = null
)

@Serializable
data class CancellationTermsItem(

    @SerialName("fulfillment_state")
    val fulfillmentState: String? = null,

    @SerialName("external_ref_url")
    val externalRefUrl: String? = null,

    @SerialName("external_ref_url_mimetype")
    val externalRefUrlMimetype: String? = null,

    @SerialName("cancellation_fee_percentage")
    val cancellationFeePercentage: String? = null
)

@Serializable
data class FulfillmentsItem(

    @SerialName("state")
    val state: State? = null,

    @SerialName("customer")
    val customer: Customer? = null

)

@Serializable
data class State(

    @SerialName("descriptor")
    val descriptor: Descriptor? = null
)

@Serializable
data class Descriptor(

    @SerialName("code")
    val code: String? = null,

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class Customer(

    @SerialName("person")
    val person: Person? = null,

    @SerialName("contact")
    val contact: Contact? = null
)

@Serializable
data class Person(

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class Contact(

    @SerialName("phone")
    val phone: String? = null
)

@Serializable
data class TagsItem(

    @SerialName("display")
    val display: Boolean? = null,

    @SerialName("descriptor")
    val descriptor: Descriptor? = null,

    @SerialName("list")
    val list: List<ListItem?>? = null
)

@Serializable
data class ListItem(

    @SerialName("descriptor")
    val descriptor: Descriptor? = null,

    @SerialName("value")
    val value: String? = null
)

@Serializable
data class Params(

    @SerialName("amount")
    val amount: String? = null,

    @SerialName("currency")
    val currency: String? = null
)

@Serializable
data class Time(

    @SerialName("range")
    val range: Range? = null,

    @SerialName("label")
    val label: String? = null
)

@Serializable
data class Range(

    @SerialName("start")
    val start: String? = null,

    @SerialName("end")
    val end: String? = null
)
