package com.example.paypay_code_chalenge.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converter {


    @TypeConverter
    @JvmStatic
    fun fromString(value: String): Map<String, Double> {
        val mapType = object : TypeToken<Map<String, Double>>() {}.type
        return Gson().fromJson(value, mapType)
    }


    @TypeConverter
    @JvmStatic
    fun fromStringMap(map: Map<String, Double>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}