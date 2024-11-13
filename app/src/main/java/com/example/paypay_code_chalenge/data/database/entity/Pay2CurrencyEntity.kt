package com.example.paypay_code_chalenge.data.database.entity

import androidx.room.*

import java.io.Serializable


@Entity(tableName = "currency")
data class Pay2CurrencyEntity(
        @PrimaryKey(autoGenerate = true) var id:Int,
        @ColumnInfo (name="rates") var rates: Map<String,Double>?,
        @ColumnInfo(name="timestamp") var timestamp: Long?

): Serializable




