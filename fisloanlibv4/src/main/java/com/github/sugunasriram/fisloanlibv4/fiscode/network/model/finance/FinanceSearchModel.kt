package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance

import kotlinx.serialization.Serializable


@Serializable
data class PFDeleteUserBodyModel(
    val loanType: String? = null,
    val mobileNumber: String? = null
)
@Serializable
data class PFSearchBodyModel(
    val loanType: String? = null,
    val bureauConsent: String? = null
)

@Serializable
data class FinanceSearchModel(
    val isFinancing: String? = null,

    val merchantIfscCode: String? = null,

    val loanType: String? = null,

    val productBrand: String? = null,

    val tnc: String? = null,

    val merchantPan: String? = null,

    val productCategory: String? = null,

    val bureauConsent: String? = null,

    val merchantBankAccountNumber: String? = null,

    val productSKUID: String? = null,

    val merchantGst: String? = null,

    val endUse: String? = null,

    val downpayment: String? = null,
    val tenure: String? = null,

    val merchantBankAccountHolderName: String? = null,

    val productPrice: String? = null,
    val productIMEI: String? = null
)

data class MerchantDetails(
    var gst: String? = null,
    var pan: String? = null,
    var bankAccountNumber: String? = null,
    var ifscCode: String? = null,
    var accountHolderName: String? = null
)