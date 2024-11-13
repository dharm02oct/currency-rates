package com.example.paypay_code_chalenge.data.network.api

import com.example.paypay_code_chalenge.data.network.model.Currencies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/latest.json")
    suspend fun getCurrencies(@Query("app_id", encoded = true) apiKey: String,@Query("base", encoded = true) base: String): Response<Currencies>
}