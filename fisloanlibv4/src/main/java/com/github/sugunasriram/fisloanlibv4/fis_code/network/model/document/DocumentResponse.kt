package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrivacyPolicyResponse(

    @SerialName("data")
    val data: List<DocumentItem?>? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class AboutUsResponse(
    @SerialName("data")
    val data: List<DocumentItem?>? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class TermsConditionResponse(

    @SerialName("data")
    val data: List<DocumentItem?>? = null,

    @SerialName("status")
    val status: Boolean? = null,

    @SerialName("statusCode")
    val statusCode: Int? = null
)

@Serializable
data class DocumentItem(

    @SerialName("paragraph_4")
    val paragraph4: String? = null,

    @SerialName("paragraph_3")
    val paragraph3: String? = null,

    @SerialName("paragraph_2")
    val paragraph2: String? = null,

    @SerialName("paragraph_1")
    val paragraph1: String? = null,

    @SerialName("subheading_1")
    val subheading1: String? = null,

    @SerialName("paragraph_8")
    val paragraph8: String? = null,

    @SerialName("subheading_2")
    val subheading2: String? = null,

    @SerialName("paragraph_7")
    val paragraph7: String? = null,

    @SerialName("paragraph_6")
    val paragraph6: String? = null,

    @SerialName("subheading_3")
    val subheading3: String? = null,

    @SerialName("subheading_3a")
    val subheading3a: String? = null,

    @SerialName("subheading_3b")
    val subheading3b: String? = null,

    @SerialName("paragraph_5")
    val paragraph5: String? = null,

    @SerialName("subheading_4")
    val subheading4: String? = null,

    @SerialName("subheading_5")
    val subheading5: String? = null,

    @SerialName("subheading_6")
    val subheading6: String? = null,

    @SerialName("subheading_7")
    val subheading7: String? = null,

    @SerialName("paragraph_9")
    val paragraph9: String? = null,

    @SerialName("subheading_8")
    val subheading8: String? = null,

    @SerialName("paragraph_13")
    val paragraph13: String? = null,

    @SerialName("paragraph_12")
    val paragraph12: String? = null,

    @SerialName("paragraph_11")
    val paragraph11: String? = null,

    @SerialName("paragraph_10")
    val paragraph10: String? = null,

    @SerialName("paragraph_17")
    val paragraph17: String? = null,

    @SerialName("paragraph_16")
    val paragraph16: String? = null,

    @SerialName("paragraph_16a")
    val paragraph16a: String? = null,

    @SerialName("paragraph_15")
    val paragraph15: String? = null,

    @SerialName("paragraph_14")
    val paragraph14: String? = null,

    @SerialName("effective_date")
    val effectiveDate: String? = null,

    @SerialName("paragraph_18")
    val paragraph18: String? = null,

    @SerialName("heading")
    val heading: String? = null,

    @SerialName("version")
    val version: String? = null,

    @SerialName("_id")
    val id: String? = null,

    @SerialName("subheading_9")
    val subheading9: String? = null,

    @SerialName("subheading_17")
    val subheading17: String? = null,

    @SerialName("subheading_18")
    val subheading18: String? = null,

    @SerialName("subheading_16")
    val subheading16: String? = null,

    @SerialName("subheading_15")
    val subheading15: String? = null,

    @SerialName("paragraph_19")
    val paragraph19: String? = null,

    @SerialName("subheading_14")
    val subheading14: String? = null,

    @SerialName("subheading_13")
    val subheading13: String? = null,

    @SerialName("subheading_12")
    val subheading12: String? = null,

    @SerialName("subheading_11")
    val subheading11: String? = null,

    @SerialName("subheading_10")
    val subheading10: String? = null,

    @SerialName("paragraph_20")
    val paragraph20: String? = null,

    @SerialName("paragraph_21")
    val paragraph21: String? = null,

    @SerialName("paragraph_22")
    val paragraph22: String? = null,

    @SerialName("paragraph_23")
    val paragraph23: String? = null,

    @SerialName("paragraph_24")
    val paragraph24: String? = null,

    @SerialName("paragraph_25")
    val paragraph25: String? = null,

    @SerialName("paragraph_26")
    val paragraph26: String? = null,

    @SerialName("paragraph_27")
    val paragraph27: String? = null,

    @SerialName("paragraph_28")
    val paragraph28: String? = null,

    @SerialName("paragraph_29")
    val paragraph29: String? = null,

    @SerialName("paragraph_30")
    val paragraph30: String? = null,

    @SerialName("paragraph_31")
    val paragraph31: String? = null,

    @SerialName("paragraph_32")
    val paragraph32: String? = null,

    @SerialName("paragraph_33")
    val paragraph33: String? = null,

    @SerialName("paragraph_34")
    val paragraph34: String? = null,

    @SerialName("paragraph_35")
    val paragraph35: String? = null,

    @SerialName("paragraph_36")
    val paragraph36: String? = null,

    @SerialName("paragraph_37")
    val paragraph37: String? = null,

    @SerialName("paragraph_38")
    val paragraph38: String? = null,

    @SerialName("paragraph_39")
    val paragraph39: String? = null,

    @SerialName("paragraph_40")
    val paragraph40: String? = null,

    @SerialName("paragraph_41")
    val paragraph41: String? = null,

    @SerialName("paragraph_42")
    val paragraph42: String? = null,

    @SerialName("paragraph_43")
    val paragraph43: String? = null,

    @SerialName("paragraph_44")
    val paragraph44: String? = null,

    @SerialName("paragraph_45")
    val paragraph45: String? = null,

    @SerialName("paragraph_46")
    val paragraph46: String? = null,

    @SerialName("paragraph_47")
    val paragraph47: String? = null,

    @SerialName("paragraph_48")
    val paragraph48: String? = null,

    @SerialName("paragraph_49")
    val paragraph49: String? = null,

    @SerialName("paragraph_50")
    val paragraph50: String? = null,

    @SerialName("paragraph_51")
    val paragraph51: String? = null,

    @SerialName("paragraph_52")
    val paragraph52: String? = null,

    @SerialName("paragraph_53")
    val paragraph53: String? = null,

    @SerialName("paragraph_54")
    val paragraph54: String? = null,

    @SerialName("paragraph_55")
    val paragraph55: String? = null,

    @SerialName("list_1")
    val list1: String? = null,

    @SerialName("list_2")
    val list2: String? = null,

    @SerialName("list_3")
    val list3: String? = null,

    @SerialName("list_4")
    val list4: String? = null,

    @SerialName("list_5")
    val list5: String? = null,

    @SerialName("list_6")
    val list6: String? = null,

    @SerialName("list_7")
    val list7: String? = null,

    @SerialName("list_8")
    val list8: String? = null,

    @SerialName("list_9")
    val list9: String? = null,

    @SerialName("list_10")
    val list10: String? = null,

    @SerialName("list_11")
    val list11: String? = null,

    @SerialName("list_12")
    val list12: String? = null,

    @SerialName("list_13")
    val list13: String? = null,

    @SerialName("list_14")
    val list14: String? = null,

    @SerialName("list_15")
    val list15: String? = null,

    @SerialName("list_16")
    val list16: String? = null,

    @SerialName("list_17")
    val list17: String? = null,

    @SerialName("list_18")
    val list18: String? = null

)
