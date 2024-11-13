package com.example.paypay_code_chalenge.data.network.model


import com.google.gson.annotations.SerializedName

data class Currencies(
    @SerializedName("timestamp")
    val timestamp: Long?,
    @SerializedName("base")
    val base: String?,
    @SerializedName("rates")
    val rates: Rates?
)