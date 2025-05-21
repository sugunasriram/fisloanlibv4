package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GstConsentResponse (
	val status: Boolean? = null,
	val data: GstData? = null,
	val statusCode: Long? = null
)

@Serializable
data class GstData (
	val offerResponse: List<OfferResponse>? = null
)

@Serializable
data class GstOfferData (
	@SerialName("_id")
	val id: String? = null,

//	val offerResponse: List<OfferResponse>? = null
	@SerialName("offer")
	val offerResponse: OfferResponse? = null
)

@Serializable
data class 	OfferResponse (
	@SerialName("user_id")
	val user_id: String? = null,

	@SerialName("doc_id")
	val docID: String? = null,

	//Sugu - STart
	@SerialName("min_loan_amount")
	val minLoanAmount: String? = null,
	@SerialName("max_loan_amount")
	val maxLoanAmount: String? = null,
	//Sugu - End

	@SerialName("_id")
	val id: String? = null,

	@SerialName("provider_id")
	val providerID: String? = null,

	@SerialName("provider_descriptor")
	val providerDescriptor: ProviderDescriptor? = null,

	@SerialName("provider_tags")
	val providerTags: List<ProviderTag>? = null,

	@SerialName("item_id")
	val itemID: String? = null,

	@SerialName("item_descriptor")
	val itemDescriptor: ItemDescriptorClass? = null,

	@SerialName("item_price")
	val itemPrice: ItemPrice? = null,

	@SerialName("item_tags")
	val itemTags: List<ItemTag>? = null,

	val parent: Parent? = null,

	@SerialName("parent_item_id")
	val parentItemID: String? = null,

	val payments: List<Payment>? = null,
	val category: List<Category>? = null,

	@SerialName("item_matched")
	val itemMatched: Boolean? = null,

	@SerialName("item_recommended")
	val itemRecommended: Boolean? = null,

	@SerialName("txn_id")
	val txnID: String? = null,

	@SerialName("msg_id")
	val msgID: String? = null,

	@SerialName("bpp_id")
	val bppID: String? = null,

	@SerialName("bpp_uri")
	val bppURI: String? = null,

	@SerialName("from_url")
	val formUrl: String? = null
)

@Serializable
data class Category (
	val id: String? = null,
	val descriptor: ItemDescriptorClass? = null
)

@Serializable
data class ItemDescriptorClass (
	val code: String? = null,
	val name: String? = null
)

@Serializable
data class ItemPrice (
	val currency: String? = null,
	val value: String? = null
)

@Serializable
data class ItemTag (
	val name: String? = null,
	val display: Boolean? = null,
	val tags: ItemTagTags? = null
)

@Serializable
data class ItemTagTags (
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
data class Parent (
	val id: String? = null,
	val descriptor: ItemDescriptorClass? = null,

	@SerialName("category_ids")
	val categoryIDS: List<String>? = null,

	val tags: List<ParentTag>? = null,
	val matched: Boolean? = null,
	val recommended: Boolean? = null
)

@Serializable
data class ParentTag (
	val descriptor: ItemDescriptorClass? = null,
	val list: List<PurpleList>? = null,
	val display: Boolean? = null
)

@Serializable
data class PurpleList (
	val descriptor: PurpleDescriptor? = null,
	val value: String? = null
)

@Serializable
data class PurpleDescriptor (
	val code: String? = null,
	val name: String? = null,

	@SerialName("short_desc")
	val shortDesc: String? = null
)

@Serializable
data class Payment (
	@SerialName("collected_by")
	val collectedBy: String? = null,

	val tags: List<PaymentTag>? = null
)

@Serializable
data class PaymentTag (
	val descriptor: FluffyDescriptor? = null,
	val display: Boolean? = null,
	val list: List<FluffyList>? = null
)

@Serializable
data class FluffyDescriptor (
	val code: String? = null
)

@Serializable
data class FluffyList (
	val descriptor: FluffyDescriptor? = null,
	val value: String? = null
)

@Serializable
data class ProviderDescriptor (
	val images: List<Image>? = null,

	@SerialName("long_desc")
	val longDesc: String? = null,

	val name: String? = null,

	@SerialName("short_desc")
	val shortDesc: String? = null
)

@Serializable
data class Image (
	@SerialName("size_type")
	val sizeType: String? = null,

	val url: String? = null
)

@Serializable
data class ProviderTag (
	val name: String? = null,
	val tags: ProviderTagTags? = null
)

@Serializable
data class ProviderTagTags (
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
