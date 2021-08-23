package com.example.adobedemoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.adobe.marketing.mobile.Analytics
import com.adobe.marketing.mobile.Lifecycle
import com.adobe.marketing.mobile.MobileCore

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("jaydev", "Called onCreate")

        MobileCore.setApplication(this)
        try{
            Lifecycle.registerExtension()
        } catch (e: Exception){
            Log.e("DemoApplication", e.toString())
        }
    }
}