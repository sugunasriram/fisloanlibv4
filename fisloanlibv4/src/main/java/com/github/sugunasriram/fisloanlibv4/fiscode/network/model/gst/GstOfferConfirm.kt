package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GstOfferConfirm(
    val id: String? = null,
    val requestAmount: String? = null,
    val loanType: String? = null
)


@Serializable
data class GstOfferConfirmResponse (
    val status: Boolean? = null,
    val data: GstOfferConfirmData? = null,
    val statusCode: Long? = null
)

@Serializable
data class GstOfferConfirmData (
    val catalog: GstCatalog? = null,
    val offerResponse: GstCatalog? = null,
    val eNACHUrlObject: GstCatalog?= null
)

@Serializable
data class GstCatalog (
    @SerialName("_id")
    val id: String? = null,

    @SerialName("parent_item_id")
    val parentItemID: String? = null,

    @SerialName("provider_id")
    val providerID: String? = null,

    @SerialName("provider_descriptor")
    val providerDescriptor: GstProviderDescriptor? = null,

    @SerialName("provider_tags")
    val providerTags: List<GstProviderTag>? = null,

    @SerialName("item_id")
    val itemID: String? = null,

    @SerialName("item_descriptor")
    val itemDescriptor: ItemDescriptor? = null,

    @SerialName("item_price")
    val itemPrice: Price? = null,

    @SerialName("item_tags")
    val itemTags: List<GstItemTag>? = null,

    @SerialName("form_id")
    val formID: String? = null,

    @SerialName("from_url")
    val fromURL: String? = null,

    @SerialName("quote_id")
    val quoteID: String? = null,

    @SerialName("quote_price")
    val quotePrice: Price? = null,

    @SerialName("quote_breakup")
    val quoteBreakup: List<QuoteBreakup>? = null,

    @SerialName("txn_id")
    val txnID: String? = null,

    @SerialName("msg_id")
    val msgID: String? = null,

    @SerialName("bpp_id")
    val bppID: String? = null,

    @SerialName("bpp_uri")
    val bppURI: String? = null,

    @SerialName("expires_at")
    val expiresAt: String? = null,

    @SerialName("SETTLEMENT_AMOUNT")
    val settlementAmount: String? = null
)

@Serializable
data class ItemDescriptor (
    val code: String? = null,
    val name: String? = null
)

@Serializable
data class Price (
    val currency: String? = null,
    val value: String? = null
)

@Serializable
data class GstItemTag (
    val name: String? = null,
    val display: Boolean? = null,
    val tags: GstItemTagTags? = null
)

@Serializable
data class GstItemTagTags (
    @SerialName("INTEREST_RATE")
    val interestRate: String? = null,

    @SerialName("TERM")
    val term: String? = null,

    @SerialName("INTEREST_RATE_TYPE")
    val interestRateType: String? = null,

    @SerialName("APPLICATION_FEE")
    val applicationFee: String? = null,

    @SerialName("FORECLOSURE_FEE")
    val foreclosureFee: String? = null,

    @SerialName("INTEREST_RATE_CONVERSION_CHARGE")
    val interestRateConversionCharge: String? = null,

    @SerialName("DELAY_PENALTY_FEE")
    val delayPenaltyFee: String? = null,

    @SerialName("OTHER_PENALTY_FEE")
    val otherPenaltyFee: String? = null,

    @SerialName("TNC_LINK")
    val tncLink: String? = null,

    @SerialName("COOL_OFF_PERIOD")
    val coolOffPeriod: String? = null,

    @SerialName("INSTALLMENT_AMOUNT")
    val installmentAmount: String? = null,

    @SerialName("ANNUAL_PERCENTAGE_RATE")
    val annualPercentageRate: String? = null,

    @SerialName("REPAYMENT_FREQUENCY")
    val repaymentFrequency: String? = null,

    @SerialName("NUMBER_OF_INSTALLMENTS_OF_REPAYMENT")
    val numberOfInstallmentsOfRepayment: String? = null,

    @SerialName("PRINCIPAL")
    val principal: String? = null,

    @SerialName("INTEREST")
    val interest: String? = null,

    @SerialName("PROCESSING_FEE")
    val processingFee: String? = null,

    @SerialName("OTHER_CHARGES")
    val otherCharges: String? = null,

    @SerialName("OFFER_VALIDITY")
    val offerValidity: String? = null,

    @SerialName("INVOICE_NUMBER")
    val invoiceNumber: String? = null
)

@Serializable
data class GstProviderDescriptor (
    val images: List<GstImage>? = null,

    @SerialName("long_desc")
    val longDesc: String? = null,

    val name: String? = null,

    @SerialName("short_desc")
    val shortDesc: String? = null
)

@Serializable
data class GstImage (
    @SerialName("size_type")
    val sizeType: String? = null,

    val url: String? = null
)

@Serializable
data class GstProviderTag (
    val name: String? = null,
    val tags: GstProviderTagTags? = null
)

@Serializable
data class GstProviderTagTags (
    @SerialName("GRO_NAME")
    val groName: String? = null,

    @SerialName("GRO_EMAIL")
    val groEmail: String? = null,

    @SerialName("GRO_CONTACT_NUMBER")
    val groContactNumber: String? = null,

    @SerialName("GRO_DESIGNATION")
    val groDesignation: String? = null,

    @SerialName("GRO_ADDRESS")
    val groAddress: String? = null,

    @SerialName("CUSTOMER_SUPPORT_LINK")
    val customerSupportLink: String? = null,

    @SerialName("CUSTOMER_SUPPORT_CONTACT_NUMBER")
    val customerSupportContactNumber: String? = null,

    @SerialName("CUSTOMER_SUPPORT_EMAIL")
    val customerSupportEmail: String? = null,

    @SerialName("LSP_NAME")
    val lspName: String? = null,

    @SerialName("LSP_EMAIL")
    val lspEmail: String? = null,

    @SerialName("LSP_CONTACT_NUMBER")
    val lspContactNumber: String? = null,

    @SerialName("LSP_ADDRESS")
    val lspAddress: String? = null
)

@Serializable
data class QuoteBreakup (
    val title: String? = null,
    val value: String? = null,
    val currency: String? = null
)
