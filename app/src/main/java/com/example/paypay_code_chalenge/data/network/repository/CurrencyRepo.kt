package com.example.paypay_code_chalenge.data.network.repository

import com.example.paypay_code_chalenge.data.network.api.ApiHelper
import com.example.paypay_code_chalenge.data.network.model.Currencies
import retrofit2.Response
import javax.inject.Inject

open class CurrencyRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getCurrencies(appId: String,base:String):Response<Currencies> = apiHelper.getCurrencies(appId,base)
}