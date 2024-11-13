package com.example.paypay_code_chalenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.paypay_code_chalenge.data.database.dao.Pay2CurrencyDao
import com.example.paypay_code_chalenge.data.database.entity.Pay2CurrencyEntity

@Database(entities = [Pay2CurrencyEntity::class],version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class Pay2CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): Pay2CurrencyDao
}