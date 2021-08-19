package com.example.adobedemoapp.AdobeAnalytics

import android.content.Context
import com.adobe.marketing.mobile.*
import java.net.URL
import kotlin.reflect.full.memberProperties

//  Issue : Todo dont know if somehow this needs to be registered already is on the main app
class AdobeAnalyticsProvider(context: Context) : AnalyticsProvider {

    data class ACPCoreExtensionEvent(val payload: MutableMap<String, Any>?) : Event

    override fun setup() {
        // registerAdobeAnalytics in MainActivity actually is called here
    }

    override fun track(event: String, trackable: Trackable, context: Map<String, String>) {
        when (trackable) {
            Trackable.VIEW -> MobileCore.trackState(event, context)
            Trackable.ACTION -> MobileCore.trackAction(event, context)
        }
    }

    override fun track(event: com.adobe.marketing.mobile.Event) {
        TODO("Not yet implemented")
    }

    override fun trackLink(url: URL) {
        TODO("Not yet implemented")
    }

    override fun send(event: com.adobe.marketing.mobile.Event) {
        processACPCoreExtension(event)
    }

    override fun appendQueryParameters(url: URL, callback: (URL) -> Unit) {
        TODO("Not yet implemented")
    }

    private fun processACPCoreExtension(event: com.adobe.marketing.mobile.Event) {
        Identity.getExperienceCloudId {
            this.setVisitorIdentifier(it)
            locationBlob = event.getField("blob")
            locationHint = event.getField("locationhint")
        }
    }

    private fun setVisitorIdentifier(identifier: String) {
        //  Implemented properly on the app needs a lot more code to get done
        Identity.syncIdentifier("wbcid", identifier, VisitorID.AuthenticationState.UNKNOWN)
    }

    companion object {
        var locationBlob : String? = null
        var locationHint : String? = null
    }
}

//  Extensions
@Throws(IllegalAccessException::class, ClassCastException::class)
inline fun <reified T> Any.getField(fieldName: String): T? {
    this::class.memberProperties.forEach { kCallable ->
        if (fieldName == kCallable.name) {
            return kCallable.getter.call(this) as T?
        }
    }
    return null
}