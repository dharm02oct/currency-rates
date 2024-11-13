package com.abhijit.paypay.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.paypay_code_chalenge.data.database.entity.Pay2CurrencyEntity
import com.example.paypay_code_chalenge.utils.TimeInHours

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {
    companion object {
        lateinit var doc: List<Pay2CurrencyEntity>

        @JvmStatic
        fun getAddress(context: Context, latitude: Double, longitude: Double): String {
            var geocoder = Geocoder(context, Locale.getDefault())
            var address = ""
            address = try {
                var addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
                addresses!![0].getAddressLine(0)

            } catch (e: IOException) {
                e.message.toString()
            }
            return address
        }

        @JvmStatic
        fun setRecentDoctor(doc: List<Pay2CurrencyEntity>) {
            this.doc = doc

        }

        @JvmStatic
        fun getRecentDoctor(): List<Pay2CurrencyEntity> {
            return doc
        }

        @JvmStatic
        fun convertFromDuration(timeInSeconds: Long?): TimeInHours? {
            var time = timeInSeconds
            val hours = time?.div(3600)
            time = time?.rem(3600)
            val minutes = time?.div(60)
            time = time?.rem(60)
            val seconds = time
            if (hours != null) {
                if (seconds != null) {
                    if (minutes != null) {
                        return TimeInHours(hours.toInt(), minutes.toInt(), seconds.toInt())
                    }
                }
            }
            if (hours != null) {
                if (minutes != null) {
                    if (seconds != null) {
                        TimeInHours(hours.toInt(), minutes.toInt(), seconds.toInt())
                    }
                }
            }
            return null
        }
       @JvmStatic
       fun getDateTime(timestamp:  Long?): String? {
            try {
                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val netDate = Date(timestamp?.times(1000) ?: 0)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }

    }


}