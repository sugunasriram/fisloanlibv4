package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan

import kotlinx.serialization.Serializable


@Serializable
data class DeleteUserResponse(
    val status: Boolean? = null,
    val data: PLSearchData? = null,
    val statusCode: Int? = null
)

@Serializable
data class LoanSearchResponse(
    val status: Boolean? = null,
    val data: PLSearchData? = null,
    val statusCode: Int? = null
)

@Serializable
data class PLSearchData(
    val message: String? = null,
    val statusCode: Int? = null
)
