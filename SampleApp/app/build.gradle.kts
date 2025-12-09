import java.util.Properties

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jlleitschuh.gradle.ktlint")
  kotlin("plugin.serialization")
  kotlin("plugin.compose")
  alias(libs.plugins.jvmTarget)
}

// Load local.properties from root directory
val localFile = project.rootDir.resolve("local.properties")
val localProperties =
  Properties().apply {
    if (localFile.exists()) {
      localFile.inputStream().use { load(it) }
    }
  }

android {
  namespace = "com.tyro.taptopay.sdk.demo"
  compileSdk = 35

  defaultConfig {
    minSdk = 26
    targetSdk = 35
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  // Update the values can be set in local.properties: keyPassword, storePassword, storeFilePath
  // to use the demo app in sandbox or production environment.
  signingConfigs {
    create("default") {
      keyAlias = "key0"
      // Support both local.properties and environment variables (for CI/CD)
      keyPassword = localProperties.getProperty("keyPassword")?.takeIf { it.isNotEmpty() }
        ?: "password"
      val keystorePath =
        localProperties.getProperty("storeFilePath")?.takeIf { it.isNotEmpty() }
          ?: "default.jks"
      storeFile = file("${project.projectDir}/$keystorePath")
      storePassword = localProperties.getProperty("storePassword")?.takeIf { it.isNotEmpty() }
        ?: "password"
    }
  }

  flavorDimensions += "default"
  productFlavors {
    create("stub") {
      resValue("string", "app_name", "SDK Demo Stub")
      resValue("string", "tap_to_pay_sdk_demo", "Tap to Pay SDK Demo Stub")
      isDefault = true
      applicationId = "com.tyro.taptopay.sdk.demo.stub"
      versionCode = 1
      versionName = "1.0"
    }
    create("dev") {
      resValue("string", "app_name", "SDK Demo UAT")
      resValue("string", "tap_to_pay_sdk_demo", "Tap to Pay SDK Demo UAT")
      applicationId = "com.tyro.taptopay.sdk.demo"
      versionCode = 7
      versionName = "1.6"
    }
    create("prd") {
      resValue("string", "app_name", "SDK Demo PVT")
      resValue("string", "tap_to_pay_sdk_demo", "Tap to Pay SDK Demo PVT")
      applicationId = "com.tyro.taptopay.sdk.demo.pvt"
      versionCode = 4
      versionName = "1.4"
    }
  }

  buildTypes {
    debug {
      signingConfig = signingConfigs.getByName("default")
    }
    release {
      signingConfig = signingConfigs.getByName("default")
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
  }
  buildFeatures {
    buildConfig = true
    compose = true
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  debugImplementation("com.tyro:tyro-tap-to-pay-sdk-debug:latest.release")
  releaseImplementation("com.tyro:tyro-tap-to-pay-sdk-release:latest.release")
  implementation(libs.accompanist.systemuicontroller)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.cio)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.serialization.kotlinx.json)
  implementation(libs.lottie)
  implementation(libs.lottie.compose)

  implementation(libs.androidx.datastore.preferences)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.compose.bom))
  implementation(libs.compose.ui)
  implementation(libs.compose.ui.graphics)
  implementation(libs.compose.ui.tooling.preview)
  implementation(libs.compose.material3)
  implementation(libs.compose.material.icons.extended)

  debugImplementation(libs.compose.ui.tooling)
  debugImplementation(libs.compose.ui.test.manifest)
}
