package com.tyro.taptopay.sdk.demo

import android.app.Application
import com.tyro.taptopay.sdk.api.TapToPaySdk
import com.tyro.taptopay.sdk.api.TyroEnv
import com.tyro.taptopay.sdk.api.TyroEnvDev
import com.tyro.taptopay.sdk.api.TyroEnvProd
import com.tyro.taptopay.sdk.api.TyroEnvStub
import com.tyro.taptopay.sdk.api.TyroOptions
import com.tyro.taptopay.sdk.api.TyroScreenOrientation
import com.tyro.taptopay.sdk.api.TyroThemeMode
import com.tyro.taptopay.sdk.api.data.PosInfo
import com.tyro.taptopay.sdk.impl.android.isMobileScreen

class SdkDemoApplication : Application() {
  lateinit var connectionProvider: SdkDemoConnectionProvider

  override fun onCreate() {
    super.onCreate()
    createTapToPaySdk()
  }

  @Suppress("KotlinConstantConditions")
  private fun createTapToPaySdk(): TapToPaySdk {
    val tyroEnv: TyroEnv =
      when (BuildConfig.FLAVOR) {
        "stub" -> {
          connectionProvider = SdkDemoConnectionProvider("https://dummy.com")
          TyroEnvStub()
        }

        "dev" -> {
          connectionProvider =
            SdkDemoConnectionProvider("https://api.tyro.com/connect/tap-to-pay/demo/connections")
          TyroEnvDev(connectionProvider)
        }

        else -> {
          // TODO: Do not use the demo connection URL in production apps
          connectionProvider =
            SdkDemoConnectionProvider("https://api.tyro.com/connect/tap-to-pay/demo/connections/pvt")
          TyroEnvProd(connectionProvider)
        }
      }
    return TapToPaySdk.createInstance(
      tyroEnv,
      applicationContext,
      TyroOptions(
        if (isMobileScreen(applicationContext)) {
          TyroScreenOrientation.PORTRAIT
        } else {
          TyroScreenOrientation.SENSOR
        },
        TyroThemeMode.SYSTEM,
      ),
    ).apply {
      setPosInfo(
        PosInfo(
          posName = "Demo",
          posVendor = "Tyro Payments",
          posVersion = "1.0",
          siteReference = "Sydney",
        ),
      )
    }
  }
}
