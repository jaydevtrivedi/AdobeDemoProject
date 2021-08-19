package com.example.adobedemoapp

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.adobe.marketing.mobile.MobileCore
import com.adobe.marketing.mobile.MobileCore.getApplication

class MyApplicationLifecycleObserver : LifecycleObserver {

    fun registerLifecycle(lifecycle : Lifecycle){
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        MobileCore.setApplication(getApplication())
        MobileCore.lifecycleStart(null)
        Log.d("OnResume","ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        MobileCore.lifecyclePause()
        Log.d("onPause","ON_PAUSE")
    }
}