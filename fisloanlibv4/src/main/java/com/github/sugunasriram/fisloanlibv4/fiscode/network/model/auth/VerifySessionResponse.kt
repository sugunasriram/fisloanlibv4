package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.ListItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.Tags
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifySessionResponse(
	val statusCode: Int,
	val status: Boolean,
	val message: String,
	val data: VerifySessionData
) : java.io.Serializable
@Serializable
data class VerifySessionData(
	val refreshToken: String,
	val sessionId: String,
	val accessToken: String,
	val sseId: String,
	val securityKey: String,
	val sessionData: VerifySessionDetails,
	val sessionType: String
) : java.io.Serializable

@Serializable
data class VerifySessionDetails(
	val downPayment: Int,
	val cartAmount: String?,
	val productId: String?,
	val merchantPAN: String?,
	val merchantGST: String?,
	val productBrand: String?,
	val merchantBankAccount: String?,
	val merchantIfscCode: String?,
	val merchantAccountHolderName: String?,
	val productCategory: String?,
	val productSKUID: String?,
	val productIMEI: String? = null,
	val productReturnWindow: String?,
	val productCancellable: Boolean,
	val productReturnable: Boolean,
	val productName: String?,
	val productMrpPrice: String?,
	val productSymbol: String?,
	val productQuantity: Int,
	val productModel: String?,
	val productSellingPrice: String?,
	val deliveryCharges: String? = null,
	val tax: String? = null,
	@SerialName("otherCharges")
	val otherCharges: List<OtherCharge>? = null
) : java.io.Serializable


@Serializable
data class OtherCharge(
	@SerialName("item_id")
	val itemId: String?,

	@SerialName("title")
	val title: String?,

	@SerialName("title_type")
	val titleType: String?,

	@SerialName("price")
	val price: Price?,

	@SerialName("items")
	val items: String?
) : java.io.Serializable

@Serializable
data class Price(
	@SerialName("currency")
	val currency: String?,

	@SerialName("value")
	val value: String?
) : java.io.Serializable