package com.github.sugunasriram.fisloanlibv4.fis_code.network.model

import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOfferData
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.Offer
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.pf.PfOffer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfferListModel(

    @SerialName("data")
    val data: List<Offer?>? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class GstOfferListModel(

    @SerialName("data")
    val data: List<GstOfferData?>? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class PfOfferListModel(

    @SerialName("data")
    val data: List<PfOffer?>? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)
