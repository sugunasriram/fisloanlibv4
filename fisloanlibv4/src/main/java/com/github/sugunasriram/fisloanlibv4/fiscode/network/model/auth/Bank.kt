package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.buildJsonObject

@Serializable
data class Bank(
    @Serializable(with = BankDataDeserializer::class)
    val data: BankData? = null,
    val status: Boolean? = null,
    val statusCode: Int? = null
)

@Serializable
data class BankAccountsItem(
    val accountType: String? = null,
    val bankAccountNumber: String? = null,
    val bankIfscCode: String? = null,
    val accountHolderName: String? = null
)

@Serializable
data class BankData(
    val id: String? = null,
    val bankAccounts: List<BankAccountsItem?>? = null
)

object BankDataDeserializer : JsonTransformingSerializer<BankData>(BankData.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return when (element) {
            is JsonObject -> element
            is JsonArray -> buildJsonObject { }
            else -> throw SerializationException("Unexpected JSON token")
        }
    }
}

