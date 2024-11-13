package com.example.paypay_code_chalenge.data.network.api

import com.example.paypay_code_chalenge.data.network.model.Currencies
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
   override suspend fun getCurrencies(appId: String , base:String): Response<Currencies> = apiService.getCurrencies(appId,base)

}