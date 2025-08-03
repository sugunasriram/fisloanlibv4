package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.pf

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PfOfferConfirm(
    val id: String? = null,
    val requestAmount: String? = null,
    val loanType: String? = null,
    val requestTerm: String? = null
)

@Serializable
data class PfOfferConfirmResponse(
    val status: Boolean? = null,
    val data: PfOfferConfirmData? = null,
    val statusCode: Long? = null
)

@Serializable
data class PfOfferConfirmData(
    val catalog: PfCatalog? = null,
    val offerResponse: PfCatalog? = null,
    val eNACHUrlObject: PfCatalog? = null
)

@Serializable
data class PfCatalog(
    @SerialName("_id")
    val id: String? = null,

    @SerialName("parent_item_id")
    val parentItemID: String? = null,

    @SerialName("provider_id")
    val providerID: String? = null,

    @SerialName("provider_descriptor")
    val providerDescriptor: PfProviderDescriptor? = null,

    @SerialName("provider_tags")
    val providerTags: List<PfProviderTag>? = null,

    @SerialName("item_id")
    val itemID: String? = null,

    @SerialName("item_descriptor")
    val itemDescriptor: ItemDescriptor? = null,

    @SerialName("item_price")
    val itemPrice: Price? = null,

    @SerialName("item_tags")
    val itemTags: List<PfItemTag>? = null,

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
data class Price(
    val currency: String? = null,
    val value: String? = null
)

@Serializable
data class PfItemTag(
    val name: String? = null,
    val display: Boolean? = null,
    val tags: PfItemTagTags? = null
)

@Serializable
data class PfItemTagTags(
    @SerialName("INTEREST_RATE")
    val interestRate: String? = null,

    @SerialName("PRINCIPAL_AMOUNT")
    val principleAmount: String? = null,

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

    @SerialName("NUMBER_OF_INSTALLMENTS")
    val numberOfInstallments: String? = null,

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
data class PfProviderDescriptor(
    val images: List<PfImage>? = null,

    @SerialName("long_desc")
    val longDesc: String? = null,

    val name: String? = null,

    @SerialName("short_desc")
    val shortDesc: String? = null
)

@Serializable
data class PfImage(
    @SerialName("size_type")
    val sizeType: String? = null,

    val url: String? = null
)

@Serializable
data class PfProviderTag(
    val name: String? = null,
    val tags: PfProviderTagTags? = null
)

@Serializable
data class PfProviderTagTags(
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
data class QuoteBreakup(
    val title: String? = null,
    val value: String? = null,
    val currency: String? = null
)
