package com.example.paypay_code_chalenge.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.paypay_code_chalenge.data.network.api.ApiHelper
import com.example.paypay_code_chalenge.data.network.model.Currencies
import com.example.paypay_code_chalenge.data.network.repository.CurrencyRepo
import com.example.paypay_code_chalenge.di.Pay2Module.APP_ID
import com.example.paypay_code_chalenge.utils.Result
import com.example.paypay_code_chalenge.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class Pay2CurrencyViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private  lateinit var apiHelper: ApiHelper

    private lateinit var currencyRepo: CurrencyRepo

    @Mock
    private lateinit var resultObserver: Observer<Result<Currencies>>

    private lateinit var viewModel:Pay2CurrencyViewModel


    @Before
    fun setUp() {
        currencyRepo = spy(CurrencyRepo(apiHelper))
        MockitoAnnotations.initMocks(this)
        viewModel = Pay2CurrencyViewModel(currencyRepo, null)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
           var body = Currencies(1233445,"USD", null)
            `when`(currencyRepo.getCurrencies(APP_ID,"USD")).thenReturn(Response.success(body))
            viewModel.currency.observeForever(resultObserver)
            verify(currencyRepo).getCurrencies(APP_ID,"USD")
            viewModel.currency.removeObserver(resultObserver)

        }
    }

}