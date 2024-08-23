package com.example.myportfolio.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerializableConversionRate(
    @SerialName("Cur_ID") val id: Long,
    @SerialName("Cur_OfficialRate") val rate: Double,
    @SerialName("Date") val date: String
)
