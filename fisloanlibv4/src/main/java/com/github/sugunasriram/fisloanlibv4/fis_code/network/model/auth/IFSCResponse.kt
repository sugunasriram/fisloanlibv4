package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class IFSCResponse(
    val BRANCH: String? = null,
    val NEFT: Boolean? = null,
    val CONTACT: String? = null,
    val BANK: String? = null,
    val IMPS: Boolean? = null,
    val RTGS: Boolean? = null,
    val STATE: String? = null,
    val SWIFT: String? = null,
    val MICR: String? = null,
    val UPI: Boolean? = null,
    val IFSC: String? = null,
    val DISTRICT: String? = null,
    val CITY: String? = null,
    val BANKCODE: String? = null,
    val ADDRESS: String? = null,
    val ISO3166: String? = null,
    val CENTRE: String? = null
)
