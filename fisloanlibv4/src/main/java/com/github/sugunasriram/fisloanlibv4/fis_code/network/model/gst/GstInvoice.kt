package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GstInvoice(
    @SerialName("data")
    val data: Data? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class Data(
    @SerialName("invoices")
    val invoices: List<InvoicesItem?>? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("gstinProfile")
    val gstinProfile: List<GstinProfileItem?>? = null
)

@Serializable
data class ItmsItem(
    @SerialName("samt")
    val samt: Double? = null, // Changed to Double

    @SerialName("csamt")
    val csamt: Int? = null,

    @SerialName("rt")
    val rt: Int? = null,

    @SerialName("txval")
    val txval: Double? = null,

    @SerialName("camt")
    val camt: Double? = null,

    @SerialName("iamt")
    val iamt: Double? = null
)

@Serializable
data class InvoicesItem(
    @SerialName("val")
    val value: Int? = null,

    @SerialName("is_auto_pop")
    val isAutoPop: Boolean? = null,

    @SerialName("itms")
    val itms: List<ItmsItem?>? = null,

    @SerialName("oinum")
    val oinum: String? = null,

    @SerialName("osbnum")
    val osbnum: String? = null,

    @SerialName("fp")
    val fp: String? = null,

    @SerialName("sbdt")
    val sbdt: String? = null,

    @SerialName("inum")
    val inum: String? = null,

    @SerialName("sbpcode")
    val sbpcode: String? = null,

    @SerialName("inv_typ")
    val invTyp: String? = null,

    @SerialName("pos")
    val pos: Int? = null,

    @SerialName("sbnum")
    val sbnum: String? = null,

    @SerialName("ctin")
    val ctin: String? = null,

    @SerialName("idt")
    val idt: String? = null,

    @SerialName("rchrg")
    val rchrg: String? = null,

    @SerialName("diff_percent")
    val diffPercent: String? = null,

    @SerialName("etin")
    val etin: String? = null,

    @SerialName("osbpcode")
    val osbpcode: String? = null,

    @SerialName("oidt")
    val oidt: String? = null,

    @SerialName("osbdt")
    val osbdt: String? = null
)

@Serializable
data class Addr(
    @SerialName("bnm")
    val bnm: String? = null,

    @SerialName("st")
    val st: String? = null,

    @SerialName("loc")
    val loc: String? = null,

    @SerialName("bno")
    val bno: String? = null,

    @SerialName("stcd")
    val stcd: String? = null,

    @SerialName("dst")
    val dst: String? = null,

    @SerialName("city")
    val city: String? = null,

    @SerialName("flno")
    val flno: String? = null,

    @SerialName("lt")
    val lt: String? = null,

    @SerialName("lg")
    val lg: String? = null,

    @SerialName("pncd")
    val pncd: String? = null
)

@Serializable
data class Pradr(
    @SerialName("addr")
    val addr: Addr? = null,

    @SerialName("ntr")
    val ntr: String? = null
)

@Serializable
data class GstinProfileItem(
    @SerialName("stjCd")
    val stjCd: String? = null,

    @SerialName("lgnm")
    val lgnm: String? = null,

    @SerialName("stj")
    val stj: String? = null,

    @SerialName("dty")
    val dty: String? = null,

    @SerialName("adadr")
    val adadr: List<AdadrItem?>? = null,

    @SerialName("gstin")
    val gstin: String? = null,

    @SerialName("nba")
    val nba: List<String?>? = null,

    @SerialName("lstupdt")
    val lstupdt: String? = null,

    @SerialName("rgdt")
    val rgdt: String? = null,

    @SerialName("ctb")
    val ctb: String? = null,

    @SerialName("pradr")
    val pradr: Pradr? = null,

    @SerialName("sts")
    val sts: String? = null,

    @SerialName("ctjCd")
    val ctjCd: String? = null,

    @SerialName("tradeNam")
    val tradeNam: String? = null,

    @SerialName("ctj")
    val ctj: String? = null,

    @SerialName("einvoiceStatus")
    val einvoiceStatus: String? = null
)

@Serializable
data class AdadrItem(
    @SerialName("addr")
    val addr: Addr? = null,

    @SerialName("ntr")
    val ntr: String? = null
)
