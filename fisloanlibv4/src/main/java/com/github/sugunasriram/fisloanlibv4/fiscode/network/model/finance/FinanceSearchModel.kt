package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance

import kotlinx.serialization.Serializable

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

    val merchantBankAccountHolderName: String? = null,

    val productPrice: String? = null
)

