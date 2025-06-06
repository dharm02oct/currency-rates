package com.example.paypay_code_chalenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.paypay_code_chalenge.ui.screens.CurrencyScreen
import com.example.paypay_code_chalenge.ui.viewmodel.Pay2CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val currencyViewModel: Pay2CurrencyViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
       currencyViewModel.fetchCurrencyRates()
      val itemModifier = Modifier.border(1.dp, Color.Gray).padding(16.dp).wrapContentSize()
      setContent {
              CurrencyScreen(itemModifier, viewModel = currencyViewModel )
      }
  }
}
