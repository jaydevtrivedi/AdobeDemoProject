package com.example.adobedemoapp.JavaInteractors;

import android.util.Log;

import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.Event;
import com.adobe.marketing.mobile.Extension;
import com.adobe.marketing.mobile.ExtensionApi;
import com.adobe.marketing.mobile.ExtensionError;
import com.adobe.marketing.mobile.ExtensionErrorCallback;
import com.adobe.marketing.mobile.ExtensionListener;
import com.adobe.marketing.mobile.Identity;

import java.util.Map;

//  Didnt receive any callbacks as a Kotlin class from AdobeCoreExtension class started receiving the callbacks
//  once it was converted to java could have something to do with AdobeSdk kotlin support

public class ACPCoreExtensionListenerJava extends ExtensionListener implements AdobeCallback<String> {

    String COM_ADOBE_MODULE_IDENTITY = "com.adobe.module.identity";
    ExtensionApi extensionApi = null;

    protected ACPCoreExtensionListenerJava(ExtensionApi extension, String type, String source) {
        super(extension, type, source);
        extensionApi = extension;
    }

    //  Step 3: Register for shared state and handle it in the event listener
    @Override
    public void hear(final Event event) {
        String STATE_OWNER = "stateowner";//no camel case
        String BLOB = "blob";
        String LOCATION_HINT = "locationhint";//no camel case

        ExtensionErrorCallback<ExtensionError> errorCallback = new ExtensionErrorCallback<ExtensionError>() {
            @Override
            public void error(final ExtensionError extensionError) {
                Log.w("Adobe", "Something went wrong, the listener couldn't be registered");
            }
        };

        if (event != null) {
            String eventOwner = event.getEventData().get(STATE_OWNER).toString();
            if (eventOwner == COM_ADOBE_MODULE_IDENTITY) {
                Identity.getExperienceCloudId(this);

                //Issue   Todo this is protected need to find out a way around this if this is required
//              if(extensionApi!=null) {
//                  extensionApi.getSharedEventState(COM_ADOBE_MODULE_IDENTITY, event);
//              }

                Map<String, Object> eventData = event.getEventData();

                Object blob = eventData.get(BLOB);
                Object locationHint = eventData.get(LOCATION_HINT);

//                if (blob != null) {
//                    AnalyticsConfiguration.INSTANCE.setLocationBlob(eventData.get(BLOB).toString());
//                }
//                if (locationHint != null) {
//                    AnalyticsConfiguration.INSTANCE.setLocationHint(eventData.get(LOCATION_HINT).toString());
//                }
//
//                AnalyticsConfiguration.INSTANCE.syncAnalyticsCookiesIfNeeded();
            }
        } else {
            return;
        }
    }

    @Override
    protected Extension getParentExtension() {
        return (AdobeCoreExtensionJava) super.getParentExtension();
    }

    @Override
    public void call(String identifier) {
        setVisitorIdentifier(identifier);
    }

    private void setVisitorIdentifier(String identifier) {
        Log.d("Adobe", "ACPCoreExtensionListenerJava " + identifier);
        //  Issue : Todo the below line syncIdenfier hits multiple demdex calls when the whole send event and stuff works
//        Identity.syncIdentifier("wbcid", identifier, AdobeAnalyticsProvider.Companion.getAuthState());

//        HashMap<String, String> globalContext = AnalyticsConfiguration.INSTANCE.getGlobalContext();
//        globalContext.put("dd.trackingId", identifier);
    }
}

