package com.example.paypay_code_chalenge.di

import android.content.Context
import androidx.room.Room
import com.example.paypay_code_chalenge.data.network.api.ApiHelper
import com.example.paypay_code_chalenge.data.network.api.ApiService
import com.example.paypay_code_chalenge.data.network.repository.CurrencyRepo
import com.example.paypay_code_chalenge.data.database.Pay2CurrencyDatabase
import com.example.paypay_code_chalenge.data.database.dao.Pay2CurrencyDao
import com.example.paypay_code_chalenge.data.network.api.ApiHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Pay2Module {
       const val BASE_URL ="https://openexchangerates.org/api/"
        const val APP_ID = "2eaa3b1844fd4c3db25ef0e67b173dd7"

    @Provides
    @Singleton
    fun provideOkHttpClient() = run {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
            okHttpClient: OkHttpClient,
    ): Retrofit =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService): ApiHelper = ApiHelperImpl(apiService)

    @Provides
    @Singleton
    fun provideCurrencyRepoHelper(apiHelper: ApiHelper): CurrencyRepo = CurrencyRepo(apiHelper)

    @Provides
    fun provideCurrencyDao(currencyDatabase: Pay2CurrencyDatabase): Pay2CurrencyDao {
        return currencyDatabase.currencyDao()
    }

    @Provides
    @Singleton
    fun provideCurrencyDatabase(@ApplicationContext appContext: Context): Pay2CurrencyDatabase {
        return Room.databaseBuilder(
                appContext,
                Pay2CurrencyDatabase::class.java,
                "currencydb"
        ).build()
    }
}
