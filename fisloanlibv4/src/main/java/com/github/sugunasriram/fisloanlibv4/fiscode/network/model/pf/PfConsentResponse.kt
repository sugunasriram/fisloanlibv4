package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf

import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CancellationTermsItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.FulfillmentsItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.PaymentsItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.Category
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.Parent
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.DocumentsItem
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class ConsentApprovalResponse(
    val data: OfferResponse? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class OfferResponse(
    val offerResponse: List<PfOffer?>? = null
)

@Serializable
data class PfOffer(
    val offer: PfOfferResponseItem? = null,
    @SerialName("_id")
    val id: String? = null
)

@Serializable
data class PfOfferResponseItem(
    @SerialName("_id")
    val id: String? = null,
    @SerialName("form_id")
    val formId: String? = null,
    @SerialName("item_descriptor")
    val itemDescriptor: ItemDescriptor? = null,
    @SerialName("item_tags")
    val itemTags: ArrayList<ItemTagsItem?>? = null,
    @SerialName("provider_descriptor")
    val providerDescriptor: ProviderDescriptor? = null,
    @SerialName("bpp_id")
    val bppId: String? = null,
    @SerialName("msg_id")
    val msgId: String? = null,

    // Sugu - STart
    @SerialName("min_loan_amount")
    val minLoanAmount: String? = null,
    @SerialName("max_loan_amount")
    val maxLoanAmount: String? = null,
    // Sugu - End

    @SerialName("quote_id")
    val quoteId: String? = null,
    @SerialName("provider_tags")
    val providerTags: ArrayList<ProviderTagsItem?>? = null,
    @SerialName("quote_price")
    val quotePrice: QuotePrice? = null,
    @SerialName("item_id")
    val itemId: String? = null,
    @SerialName("bpp_uri")
    val bppUri: String? = null,
    @SerialName("from_url")
    var formUrl: String? = null,
    @SerialName("provider_id")
    val providerId: String? = null,
    @SerialName("item_price")
    val itemPrice: ItemPrice? = null,
    @SerialName("txn_id")
    val txnId: String? = null,
    @SerialName("payments")
    val payments: List<PaymentsItem?>? = null,
    @SerialName("cancellation_terms")
    val cancellationTerms: List<CancellationTermsItem?>? = null,
    @SerialName("fulfillments")
    val fulfillments: List<FulfillmentsItem?>? = null,
    @SerialName("documents")
    val documents: List<DocumentsItem?>? = null,
    @SerialName("order_id")
    val orderId: String? = null,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("doc_id")
    val docId: String? = null,
    val createdAt: String? = null,
    @SerialName("__v")
    val v: Int? = null,
    val updatedAt: String? = null,
    val parent: Parent? = null,
    @SerialName("parent_item_id")
    val parentItemID: String? = null,
    val category: List<Category>? = null,
    @SerialName("item_matched")
    val itemMatched: Boolean? = null,
    @SerialName("item_recommended")
    val itemRecommended: Boolean? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("domain")
    val domain: String? = null
)

@Serializable
data class ItemPrice(
    val currency: String? = null,
    val value: String? = null
)

@Serializable
data class Tags(

    @SerialName("Other Penal Charges")
    val otherPenalCharges: String? = null,
    @SerialName("Delayed payments penalty")
    val delayedPaymentsPenalty: String? = null,
    @SerialName("Interest rate type")
    val interestRateType: String? = null,
    @SerialName("Annual Percentage Rate")
    val annualPercentageRate: String? = null,
    @SerialName("Cool off period")
    val coolOffPeriod: String? = null,
    @SerialName("Installment amount")
    val installmentAmount: String? = null,
    @SerialName("Interest rate conversion charges")
    val interestRateConversionCharges: String? = null,
    @SerialName("Foreclosure Penalty")
    val foreclosurePenalty: String? = null,
    @SerialName("Interest Rate")
    val interestRate: String? = null,
    @SerialName("Loan Term")
    val loanTerm: String? = null,
    @SerialName("Number of installments of repayment")
    val numberOfInstallmentsOfRepayment: String? = null,
    @SerialName("Repayment Frequency")
    val repaymentFrequency: String? = null,
    @SerialName("Terms & Conditions")
    val termsConditions: String? = null,
    @SerialName("Application Fees")
    val applicationFees: String? = null,
    @SerialName("Lsp Address")
    val lspAddress: String? = null,
    @SerialName("Lsp name")
    val lspName: String? = null,
    @SerialName("Lsp email")
    val lspEmail: String? = null,
    @SerialName("Lsp contact number")
    val lspContactNumber: String? = null,
    @SerialName("Gro contact number")
    val groContactNumber: String? = null,
    @SerialName("Gro email")
    val groEmail: String? = null,
    @SerialName("Gro Designation")
    val groDesignation: String? = null,
    @SerialName("Customer support email")
    val customerSupportEmail: String? = null,
    @SerialName("Gro Address")
    val groAddress: String? = null,
    @SerialName("Customer support link")
    val customerSupportLink: String? = null,
    @SerialName("Gro name")
    val groName: String? = null,
    @SerialName("Customer support contact number")
    val customerSupportContactNumber: String? = null,
    @SerialName("Consent Handler")
    val consentHandler: String? = null

)

object TagsSerializer : KSerializer<List<Tag>> {
    override val descriptor: SerialDescriptor =
        ListSerializer(Tag.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: List<Tag>) {
        val map = value.associate { it.key to it.value }
        encoder.encodeSerializableValue(MapSerializer(String.serializer(), String.serializer()), map)
    }

    override fun deserialize(decoder: Decoder): List<Tag> {
        val map = decoder.decodeSerializableValue(MapSerializer(String.serializer(), String.serializer()))
        return map.map { Tag(it.key, it.value) }
    }
}

@Serializable
data class Tag(
    val key: String,
    val value: String
)

@Serializable
data class ProviderTagsItem(
    val name: String? = null,
    val tags: Map<String, String>? = null,
    val display: String? = null
)

@Serializable
data class ImagesItem(
    @SerialName("size_type")
    val sizeType: String? = null,
    val url: String? = null
)

@Serializable
data class ProviderDescriptor(
    val images: ArrayList<ImagesItem?>? = null,
    val name: String? = null,
    @SerialName("short_desc")
    val shortDesc: String? = null,
    @SerialName("long_desc")
    val longDesc: String? = null
)

@Serializable
data class ItemDescriptor(
    val code: String? = null,
    val name: String? = null
)

@Serializable
data class AAQuoteBreakUp(
    val currency: String? = null,
    val title: String? = null,
    val value: String? = null
)

@Serializable
data class QuotePrice(
    val currency: String? = null,
    val value: String? = null
)

@Serializable
data class ItemTagsItem(
    val display: Boolean? = null,
    val name: String? = null,
    @Serializable(with = TagsSerializer::class)
    val tags: List<Tag>
)

@Serializable
data class ConsentApprovalRequest(
    val id: String? = null,
    val url: String? = null,
    val loanType: String? = null
)
