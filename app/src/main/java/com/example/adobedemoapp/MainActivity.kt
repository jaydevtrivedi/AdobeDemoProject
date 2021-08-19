package com.example.adobedemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.adobe.marketing.mobile.*
import com.example.adobedemoapp.JavaInteractors.AdobeCoreExtensionJava
import com.example.adobedemoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val ADOBE_CONFIG_FILE_NAME = "adobeCoreConfig.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initUI()
    }

    private fun initUI(){
        binding.mainButton.setOnClickListener {
            registerAdobeAnalytics()
        }
    }

    private fun registerAdobeAnalytics() {
        MobileCore.setApplication(application)
        MobileCore.setLogLevel(LoggingMode.DEBUG)

        try {
            val errorCallback =
                ExtensionErrorCallback<ExtensionError> { extensionError ->
                    Log.e(
                        "Extensions",
                        String.format(
                            "An error occurred while registering the Extension %d %s",
                            extensionError.errorCode,
                            extensionError.errorName
                        )
                    )
                }

            UserProfile.registerExtension()
            Identity.registerExtension()
            Lifecycle.registerExtension()
            Signal.registerExtension()
            Analytics.registerExtension()
            Audience.registerExtension()

            // Step 2: Code to registerExtension
            if (!MobileCore.registerExtension(AdobeCoreExtensionJava::class.java, errorCallback)) {
                Log.e("Extensions", "Failed to register the MyCustomExtension extension")
            }

            MobileCore.start {
                MobileCore.configureWithFileInAssets(ADOBE_CONFIG_FILE_NAME)
            }
//            Identity.getExperienceCloudId {
//                AnalyticsConfiguration.analyticsId = it
//            }
        } catch (exp: InvalidInitException) {
            // TODO - Add appropriate exception handling
            Log.e("adobe", exp.toString())
        }
    }


}