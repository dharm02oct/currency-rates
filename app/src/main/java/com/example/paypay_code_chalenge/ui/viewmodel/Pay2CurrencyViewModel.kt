package com.example.paypay_code_chalenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypay_code_chalenge.utils.NetworkHelper
import com.example.paypay_code_chalenge.utils.Result
import com.example.paypay_code_chalenge.data.database.Pay2CurrencyDatabase
import com.example.paypay_code_chalenge.data.database.entity.Pay2CurrencyEntity
import com.example.paypay_code_chalenge.data.network.model.Currencies
import com.example.paypay_code_chalenge.data.network.repository.CurrencyRepo
import com.example.paypay_code_chalenge.di.Pay2Module.APP_ID
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class Pay2CurrencyViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepo,
    private val currencyDb: Pay2CurrencyDatabase?,
) : ViewModel() {
    var currencyNames = mutableListOf<String>()
    private val currencies = MutableLiveData<Result<List<Pay2CurrencyEntity>>>()
    private val _currency = MutableLiveData<Result<Currencies>>()
    val currency: LiveData<Result<Currencies>>
        get() = _currency

    private val _all = MutableLiveData<MutableList<Pay2CurrencyEntity>>()
    private val all: LiveData<MutableList<Pay2CurrencyEntity>>
        get() = _all
   private val _currencyList = MutableLiveData<MutableList<String>>()
     val currencyList: LiveData<MutableList<String>>
        get() = _currencyList
    private val _currencyMap = MutableLiveData<MutableMap<String,Double>>()
    val currencyMap: LiveData<MutableMap<String,Double>>
        get() = _currencyMap
    var currencyMapLocal: HashMap<String, Double> = HashMap<String, Double>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    public fun fetchCurrencyRates() {
        viewModelScope.launch {
            _currency.postValue(Result.loading(null))
            _isLoading.value = true
           /* if (networkHelper.isNetworkConnected()) {*/
               withContext(Dispatchers.IO){ currencyRepo.getCurrencies(
                    APP_ID,"USD"

                )}.let {
                    if (it.isSuccessful) {
                     println("curencyName resonse : ${it.body()}")
                        _currency.postValue(Result.success(it.body()))
                    } else {
                        _isLoading.value = false
                        _currency.postValue(Result.error(it.errorBody().toString(), null))
                    }
                }
           /* } else {
                _isLoading.value = false
                _currency.postValue(Result.error("No internet connection", null))
            }*/

        }
    }


    fun getCurrencies(): LiveData<Result<List<Pay2CurrencyEntity>>> {
        return currencies
    }

    suspend fun insertCurrencies(currency: Pay2CurrencyEntity) {
        withContext(Dispatchers.IO) {

        }
    }

     private fun performDbOps() {
        viewModelScope.launch {
            val differ1= async {
              withContext(Dispatchers.IO) {
                  currencyDb?.currencyDao()?.deleteAll()

                  var doc = Pay2CurrencyEntity(0, currencyMapLocal, System.currentTimeMillis() / 1000)
                  currencyDb?.currencyDao()?.insert(doc)
              }
          }
             differ1.await()

           val  differ2 = async {
              val dbData = withContext(Dispatchers.IO) {
                   currencyDb?.currencyDao()?.getAll()

               }

               var localData = dbData?.get(0)?.rates
               if (localData != null) {
                   for ((key, value) in localData) {
                       currencyMapLocal.put(key, value)
                   }
               }
               _isLoading.value = false
               _currencyMap.value = currencyMapLocal
               println("curencyName list model : $currencyNames")
           }

            differ2.await()

         }

    }

        fun parseJsonAndInsertData(currencies: Result<Currencies>){
            println("currencies json: $currencies")
            if (currencies.data == null ) return
            var obj = Gson().toJsonTree(currencies.data?.rates).getAsJsonObject()
            for ((key, value) in obj.entrySet()) {
               // currencyNames.add(key)
               // currencyValue.add(value.asDouble)
                currencyMapLocal.put(key, value.asDouble)
                println("Key = $key Value = $value")
            }
            performDbOps()

        }

    fun calculateValue(price:Int){
        viewModelScope.launch {
          val local =  withContext(Dispatchers.IO) {
               val dbData = currencyDb?.currencyDao()?.getAll()
                var localData = dbData?.get(0)?.rates
                if (localData != null) {
                    for ((key, value) in localData) {
                        currencyMapLocal[key] = value*price

                    }
                }
                currencyMapLocal
            }
            _currencyMap.value = local

        }



    }


}