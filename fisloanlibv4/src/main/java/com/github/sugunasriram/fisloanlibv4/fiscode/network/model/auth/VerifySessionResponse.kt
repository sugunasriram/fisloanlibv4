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
	val productId: String?,
	val loanId: String? = null,
	val merchantPAN: String?,
	val merchantGST: String?,
	val productBrand: String?,
	val merchantBankAccount: String?,
	val merchantIfscCode: String?,
	val merchantAccountHolderName: String?,
	val productCategory: String?,
	val productSKUID: String?,
	val productReturnWindow: String?,
	val productCancellable: Boolean,
	val productReturnable: Boolean,
	val productName: String?,
	val productMrpPrice: String?,
	val productSymbol: String?,
	val productQuantity: String?,
	val productModel: String?,
	val productSellingPrice: String?
) : java.io.Serializable
