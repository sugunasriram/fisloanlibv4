package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import kotlinx.serialization.Serializable
@Serializable
data class PincodeModel(
    val pincode: String? = null,
    val cities: List<String>? = null,
    val district: String? = null,
    val state: String? = null
)

@Serializable
data class AddressModel(
    val area: String? = null,
    val district: String? = null,
    val city: String? = null,
    val state: String? = null,
    val pincode: String? = null
)
