package com.github.sugunasriram.fisloanlibv4.fis_code.network.model

import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OfferResponseItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(

    @SerialName("data")
    val data: StatusData? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class StatusData(

    @SerialName("requestId")
    val requestId: String? = null,

    @SerialName("data")
    val data: DataStatus? = null
)

@Serializable
data class DataStatus(

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("data")
    val data: UserStatusData? = null
)

@Serializable
data class UserStatusData(
    @SerialName("catalog")
    val catalog: OfferResponseItem? = null,

    @SerialName("id")
    val id: String? = null
)
