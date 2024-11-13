package com.example.paypay_code_chalenge.data.network.api

import com.example.paypay_code_chalenge.data.network.model.Currencies
import retrofit2.Response


interface ApiHelper {
    //can with as non blocking
   suspend fun getCurrencies(appId: String, base:String): Response<Currencies>
}


