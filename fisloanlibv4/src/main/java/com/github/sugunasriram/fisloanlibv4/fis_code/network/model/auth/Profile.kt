package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val data: Profile? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class Profile(
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val dob: String? = null,
    val mobileNumber: String? = null,
    val countryCode: String? = null,
    val panNumber: String? = null,
    val email: String? = null,
    val income: String? = null,
    val pincode1: String? = null,
    val pincode2: String? = null,
    val role: String? = null,
    val gender: String? = null,
    val employmentType: String? = null,
    val address2: String? = null,
    val city1: String? = null,
    val city2: String? = null,
    val address1: String? = null,
    val companyName: String? = null,
    val udyamNumber: String? = null,
    val state1: String? = null,
    val state2: String? = null,
    val officialEmail: String? = null
)
