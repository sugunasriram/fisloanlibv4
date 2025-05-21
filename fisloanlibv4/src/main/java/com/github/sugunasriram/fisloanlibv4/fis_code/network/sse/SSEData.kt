package com.github.sugunasriram.fisloanlibv4.fis_code.network.sse

import android.content.om.OverlayManagerTransaction
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.Descriptor
import kotlinx.serialization.Serializable

@Serializable
data class SSEData(
    val sseId: String? = null,
    val data: DataContent? = null
)

@Serializable
data class DataContent(
    val status: Boolean? = false,
    val data: EventData? = null,
    val statusCode: Int? = null
)

@Serializable
data class EventData(
    val transactionId: String? = null, //Sugu - To remove
    val txnId: String? = null,
    val type: String? = null,
    val data: Catalog? = null,
    val catalog: Catalog? = null,
    val postRequestId: String? = null,
    val consent: Boolean? = false,
    val id: String? = null,
    val url: String? = null,

)

@Serializable
data class ErrorObj(
    val code: Int? = null,
    val message: String? = null
)

@Serializable
data class Catalog(
    val provider_id: String? = null,
    val payment_id: String? = null,
    val provider_descriptor: SSEProviderDescriptor? = null,
    val provider_tags: List<ProviderTag>? = null,
    val item_id: String? = null,
    val item_descriptor: SSEItemDescriptor? = null,
    val item_price: Price? = null,
    val item_tags: List<ItemTag>? = null,
    val form_id: String? = null,
    val from_url: String? = null,
    val quote_id: String? = null,
    val quote_price: Price? = null,
    val payments: List<Payment>? = null,
    val cancellation_terms: List<CancellationTerm>? = null,
    val txn_id: String? = null,
    val msg_id: String? = null,
    val bpp_id: String? = null,
    val bpp_uri: String? = null,
    val fulfillments: List<Fulfillment>? = null,
    val SETTLEMENT_AMOUNT: String? = null,
    val quote_breakup: List<SSEQuoteBreakUp>? = null,

    val error: ErrorObj? = null,

    )

@Serializable
data class SSEProviderDescriptor(
    val images: List<Image>? = null,
    val long_desc: String? = null,
    val name: String? = null,
    val short_desc: String? = null
)

@Serializable
data class Image(
    val size_type: String? = null,
    val url: String? = null
)

@Serializable
data class ProviderTag(
    val name: String? = null,
    val tags: Map<String, String?>? = null
)

@Serializable
data class SSEItemDescriptor(
    val code: String? = null,
    val name: String? = null
)

@Serializable
data class Price(
    val currency: String? = null,
    val value: String? = null
)

@Serializable
data class ItemTag(
    val name: String? = null,
    val display: Boolean? = null,
    val tags: Map<String, String?>? = null
)



@Serializable
data class Payment(
    val id: String? = null,
    val type: String? = null,
    val status: String? = null,
    val collected_by: String? = null,
    val tags: List<SSETag>? = null,
    val params: SSEParams? = null,
    val time: SSETime? = null
)

@Serializable
data class SSETag(
    val descriptor: SSEDescriptor? = null,
    val display: Boolean? = null,
    val list: List<TagList>? = null,
    val value: String? = null
)

@Serializable
data class SSEDescriptor(
    val code: String? = null
)

@Serializable
data class TagList(
    val descriptor: Descriptor? = null,
    val value: String? = null
)

@Serializable
data class SSEParams(
    val amount: String? = null,
    val currency: String? = null
)

@Serializable
data class SSETime(
    val label: String? = null,
    val range: TimeRange? = null
)

@Serializable
data class TimeRange(
    val start: String? = null,
    val end: String? = null
)

@Serializable
data class CancellationTerm(
    val fulfillment_state: String? = null,
    val external_ref_url: String? = null,
    val external_ref_url_mimetype: String? = null,
    val cancellation_fee_percentage: String? = null
)

@Serializable
data class Fulfillment(
    val customer: SSECustomer? = null,
    val state: FulfillmentState? = null
)

@Serializable
data class SSECustomer(
    val person: SSEPerson? = null,
    val contact: SSEContact? = null
)

@Serializable
data class SSEPerson(
    val name: String? = null
)

@Serializable
data class SSEContact(
    val phone: String? = null,
    val email: String? = null
)

@Serializable
data class FulfillmentState(
    val descriptor: FulfillmentDescriptor? = null
)

@Serializable
data class FulfillmentDescriptor(
    val code: String? = null,
    val name: String? = null
)

@Serializable
data class SSEQuoteBreakUp(
    val title: String? = null,
    val value: String? = null,
    val currency: String? = null
)
