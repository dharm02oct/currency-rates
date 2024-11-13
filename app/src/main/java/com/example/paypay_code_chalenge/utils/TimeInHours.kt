package com.example.paypay_code_chalenge.utils

class TimeInHours(val hours: Int, val minutes: Int, val seconds: Int) {
    override fun toString(): String {
        return String.format("%dh : %02dm : %02ds", hours, minutes, seconds)
    }
}