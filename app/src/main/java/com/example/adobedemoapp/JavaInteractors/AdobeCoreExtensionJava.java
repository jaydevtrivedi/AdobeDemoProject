package com.example.adobedemoapp.JavaInteractors;

import com.adobe.marketing.mobile.Extension;
        import com.adobe.marketing.mobile.ExtensionApi;
        import com.adobe.marketing.mobile.ExtensionError;
        import com.adobe.marketing.mobile.ExtensionErrorCallback;

import android.util.Log;

//  Init method for the kotlin class  on this encountered the following error from SDK
//  Also no callbacks in listener were being received

//2021-08-16 12:00:29.159 3207-6099/org.westpac.bank.debug E/AdobeExperienceSDK: EventHub(AMSEventHub) -
// Unable to create instance of provided extension AdobeCoreExtension: java.lang.NoSuchMethodException:
// au.com.westpac.banking.universal.android.analytics.AdobeAnalyticsProvider$AdobeCoreExtension.<init> [class com.adobe.marketing.mobile.ExtensionApi]

// Hence converting the class to java class with constructor, as the SDK expects a java class
// public static boolean registerExtension(Class<? extends Extension> extensionClass, ExtensionErrorCallback<ExtensionError> errorCallback)

// Could be changed to Kotlin class if init code could somehow be added before conversion takes place


// Step 1 : Build an extension
public class AdobeCoreExtensionJava extends Extension {

    public AdobeCoreExtensionJava(final ExtensionApi moduleApi) {
        super(moduleApi);

        try {
            ExtensionErrorCallback<ExtensionError> errorCallback = new ExtensionErrorCallback<ExtensionError>() {
                @Override
                public void error(final ExtensionError extensionError) {
                    Log.e("Error","Something went wrong, the listener couldn't be registered");
                }
            };

            getApi().registerEventListener(
                    "com.adobe.eventType.hub",
                    "com.adobe.eventSource.sharedState",
                    ACPCoreExtensionListenerJava.class,
                    errorCallback
            );
        } catch (Exception e) {
            Log.e("Error",e.toString());
        }
    }

    @Override
    protected String getName() {
        return "westpac";
    }

    @Override
    protected String getVersion() {
        return "1.0.0";
    }

    @Override
    protected void onUnregistered() {
        // this method will be called when the extension is unregistered from the
        // Event Hub in order for you to perform the necessary cleanup
        super.onUnregistered();
    }
}