package com.tyro.taptopay.sdk.demo

import android.app.Application
import com.tyro.taptopay.sdk.api.TapToPaySdk
import com.tyro.taptopay.sdk.api.TapToPaySdk.Companion.createInstance
import com.tyro.taptopay.sdk.api.TyroEnv
import com.tyro.taptopay.sdk.api.TyroEnvDev
import com.tyro.taptopay.sdk.api.TyroEnvProd
import com.tyro.taptopay.sdk.api.TyroEnvStub
import com.tyro.taptopay.sdk.api.TyroOptions
import com.tyro.taptopay.sdk.api.TyroScreenOrientation
import com.tyro.taptopay.sdk.api.data.PosInfo

class SdkDemoApplication : Application() {
    lateinit var tapToPaySDK: TapToPaySdk
    val connectionProvider = SdkDemoConnectionProvider()


    override fun onCreate() {
        super.onCreate()
        tapToPaySDK = createTapToPaySdk()
    }

    @Suppress("KotlinConstantConditions")
    private fun createTapToPaySdk(): TapToPaySdk {
        val tyroEnv: TyroEnv =
            when (BuildConfig.FLAVOR) {
                "stub" -> TyroEnvStub()
                "dev" ->
                    TyroEnvDev(
                        connectionProvider = connectionProvider,
                    )
                else ->
                    TyroEnvProd(
                        connectionProvider = connectionProvider,
                    )
            }
        return createInstance(tyroEnv, applicationContext, TyroOptions(TyroScreenOrientation.PORTRAIT)).apply {
            setPosInfo(PosInfo(
                posName = "Demo",
                posVendor = "Tyro Payments Example App",
                posVersion = "1.0",
                siteReference = "Sydney",
            ))
        }
    }
}
