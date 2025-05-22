package com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth

import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.ListItem
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.Tags
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankDetail(
    val accountNumber: String? = null,
    val accountHolderName: String? = null,
    val ifscCode: String? = null,
    val accountType: String? = null,
    val id: String? = null,
    val loanType: String? = null
)

@Serializable
data class BankDetailResponse(
    val data: EachResponse? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class EachResponse(
    val id: String? = null,
    val eNACHUrlObject: OfferResponseItem? = null
)

@Serializable
data class PaymentsItem(
    val id: String? = null,
    val collectedBy: String? = null,
    val type: String? = null,
    val status: String? = null,
    val tags: List<TagsItem?>? = null,
    val time: Time? = null,
    val params: Params? = null,
    val url: String? = null
)

@Serializable
data class TagsItem(
    val display: Boolean? = null,
    val name: String? = null,
    val tags: Tags? = null,
    val descriptor: Descriptor? = null,
    val list: List<ListItem?>? = null
)

@Serializable
data class CancellationTermsItem(
    val fulfillmentState: String? = null,
    @SerialName("external_ref_url")
    val externalRefUrl: String? = null,
    @SerialName("cancellation_fee_percentage")
    val cancellationFeePercentage: String? = null,
    val externalRefUrlMimetype: String? = null
)

@Serializable
data class FulfillmentsItem(
    val state: State? = null,
    val customer: Customer? = null
)

@Serializable
data class State(
    val descriptor: Descriptor? = null
)

@Serializable
data class Customer(
    val person: Person? = null,
    val contact: Contact? = null,
    val name: String? = null
)

@Serializable
data class Descriptor(
    val code: String? = null,
    val name: String? = null,
    val shortDesc: String? = null,
    val longDesc: String? = null
)

@Serializable
data class Contact(
    val phone: String? = null,
    val email: String? = null
)

@Serializable
data class Person(
    val name: String? = null
)

@Serializable
data class Time(
    val range: Range? = null,
    val label: String? = null
)

@Serializable
data class Range(
    val start: String? = null,
    val end: String? = null
)

@Serializable
data class Params(
    val amount: String? = null,
    val currency: String? = null
)

@Serializable
data class AddBankDetail(
    val account_holder_name: String? = null,
    val bank_account_number: String? = null,
    val account_type: String? = null,
    val bank_ifsc_code: String? = null
)

@Serializable
data class AddBankDetailResponse(
    val status: Boolean? = null,
    val data: String? = null,
    val statusCode: Int? = null
)

@Serializable
data class GstBankDetail(
    val accountNumber: String? = null,
    val accountHolderName: String? = null,
    val ifscCode: String? = null,
    val id: String? = null,
    val loanType: String? = null
)

@Serializable
data class PfBankDetail(
    val accountNumber: String? = null,
    val accountHolderName: String? = null,
    val ifscCode: String? = null,
    val id: String? = null,
    val loanType: String? = null
)

@Serializable
data class PfAddBankDetail(
    @SerialName("account_holder_name")
    val accountHolderName: String? = null,

    @SerialName("account_type")
    val accountType: String? = null,

    @SerialName("bank_ifsc_code")
    val ifscCode: String? = null,

    @SerialName("bank_account_number")
    val bankAccountNumber: String? = null
)
