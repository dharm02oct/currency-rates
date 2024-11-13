package com.example.paypay_code_chalenge.ui.screens
import DropDownSpinner
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.paypay_code_chalenge.utils.Status
import com.example.paypay_code_chalenge.ui.component.SearchField
import com.example.paypay_code_chalenge.ui.component.progress
import com.example.paypay_code_chalenge.ui.viewmodel.Pay2CurrencyViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun CurrencyScreen(
    itemModifier: Modifier , viewModel:Pay2CurrencyViewModel){

    var currency = viewModel.currency.observeAsState().value
    when(currency?.status){
        Status.SUCCESS ,
        Status.LOADING -> {
            viewModel.parseJsonAndInsertData(currency)
        }
        Status.ERROR ->{
            Toast.makeText(LocalContext.current,"Something went wrong ",Toast.LENGTH_LONG)
        }
            null -> {

            }
    }
    var isLoading = viewModel.isLoading.observeAsState().value?:false
    var currencyMap = viewModel.currencyMap.observeAsState().value?: mutableMapOf()
    println("isLoading: $isLoading")
    if (isLoading){
   progress()
    }else {

        Column(verticalArrangement = Arrangement.Bottom) {
            SearchField(
                searchQuery = "",
                onQueryChanged = {
                    println(" onQueryChanged $it")
                 viewModel.calculateValue(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
            )
            DropDownSpinner(currencyMap.keys.toMutableList())
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                    items(currencyMap.keys.toList()) { item ->
                        println("items $item")
                        Column(itemModifier.wrapContentSize()) {
                            Text("$item", Modifier.fillMaxWidth().fillMaxHeight())
                            Text(
                                " ${currencyMap[item]}",
                                Modifier.fillMaxWidth().fillMaxHeight()
                            )
                        }
                    }
            }
        }
    }
}