package com.example.paypay_code_chalenge.data.database.dao

import androidx.room.*
import com.example.paypay_code_chalenge.data.database.entity.Pay2CurrencyEntity


@Dao
interface Pay2CurrencyDao {

    @Query("SELECT * FROM currency")
    fun getAll(): MutableList<Pay2CurrencyEntity>

    @Insert
    fun insertAll(doctor: List<Pay2CurrencyEntity>)

    @Delete
    fun deleteAll(doctor: Pay2CurrencyEntity)

    @Query("DELETE FROM currency")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(doc: Pay2CurrencyEntity)


}