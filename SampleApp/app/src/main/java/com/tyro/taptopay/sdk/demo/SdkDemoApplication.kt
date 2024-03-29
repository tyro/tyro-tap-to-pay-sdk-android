package com.tyro.taptopay.sdk.demo

import android.app.Application
import com.tyro.taptopay.sdk.api.TapToPaySdk
import com.tyro.taptopay.sdk.api.TyroEnv
import com.tyro.taptopay.sdk.api.TyroEnvDev
import com.tyro.taptopay.sdk.api.TyroEnvProd
import com.tyro.taptopay.sdk.api.TyroEnvStub
import com.tyro.taptopay.sdk.api.data.PosInfo

class SdkDemoApplication : Application() {
    val tapToPaySDK = createTapToPaySdk()

    @Suppress("KotlinConstantConditions")
    private fun createTapToPaySdk(): TapToPaySdk {
        val tyroEnv: TyroEnv =
            when (BuildConfig.FLAVOR) {
                "stub" -> TyroEnvStub()
                "dev" ->
                    TyroEnvDev(
                        posInfo = POS_INFO,
                        connectionProvider = SdkDemoConnectionProvider(),
                    )

                else ->
                    TyroEnvProd(
                        posInfo = POS_INFO,
                        connectionProvider = SdkDemoConnectionProvider(),
                    )
            }
        return TapToPaySdk.createInstance(tyroEnv)
    }
}

val POS_INFO =
    PosInfo(
        posName = "Demo",
        posVendor = "Tyro Payments Example App",
        posVersion = "1.0",
        siteReference = "Sydney",
    )
