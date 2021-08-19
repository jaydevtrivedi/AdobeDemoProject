package com.example.adobedemoapp.AdobeAnalytics

import com.adobe.marketing.mobile.Event
import java.net.URL

/**
 * Interface used to provide analytics capability
 */
interface AnalyticsProvider {
    /**
     * Performs initial setup AnalyticsProvider
     */
    fun setup()

    /**
     * Track analytics event
     * @param event - analytics event
     * @param trackable - trackable type [Trackable]
     * @param context - context of analytics event
     */
    fun track(event: String, trackable: Trackable, context: Map<String, String>)

    /**
     * Track Link analytics event
     * @param url - url to be tracked
     */
    fun trackLink(url: URL)

    fun send(event: Event)

    /**
     * Track analytics event
     * @param event - event to be tracked
     */
    fun track(event: Event)

    /**
     * Append query parameters
     * @param url - append query params to URL
     * @param callback - callback function with transformed url
     */
    fun appendQueryParameters(url: URL, callback: (URL) -> Unit)

    fun identifiersMap(): Map<String, String> {
        return emptyMap()
    }
}

/**
 * Trackable events are:
 * - Use [Trackable.VIEW] for view event
 * - Use [Trackable.ACTION] for action event
 */
public enum class Trackable {
    VIEW, ACTION
}
