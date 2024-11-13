package com.example.paypay_code_chalenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Pay2Application : Application()
//very important to declare this in AndroidManifest.xml - android:name=".App"