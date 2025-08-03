package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import androidx.compose.ui.focus.FocusRequester
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val data: Profile? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

data class ProfileFocusRequester(
    val panFocus: FocusRequester,
    val firstNameFocus: FocusRequester,
    val lastNameFocus: FocusRequester,
    val personalEmailIdFocus: FocusRequester,
    val officialEmailIdFocus: FocusRequester,
    val employeeTypeFocus: FocusRequester,
    val dobFocus: FocusRequester,
    val genderFocus: FocusRequester,
    val companyNameFocus: FocusRequester,
    val udyamNumberFocus: FocusRequester
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
    val officialEmail: String? = null,
    val income: String? = null,
    val role: String? = null,
    val gender: String? = null,
    val employmentType: String? = null,
    val companyName: String? = null,
    val udyamNumber: String? = null,
    val address1: String? = null,
    val address2: String? = null,
    val city1: String? = null,
    val state1: String? = null,
    val pincode1: String? = null,
    val city2: String? = null,
    val state2: String? = null,
    val pincode2: String? = null,
    val statements: List<String>? = null,
    val invoices: List<String>? = null
)
