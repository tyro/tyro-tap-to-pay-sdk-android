plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.tyro.taptopay.sdk.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tyro.taptopay.sdk.demo" // DO NOT CHANGE THIS if using sampleappkeystore.jks
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // TODO update this with your own signing config in order to run against the dev or prod environment
    signingConfigs {
        create("default") {
            keyAlias = "key0" // DO NOT CHANGE THIS if using sampleappkeystore.jks
            keyPassword = "password" // UPDATE THIS if using sampleappkeystore.jks
            storeFile = file("update_this_with_your_own.jks") // UPDATE THIS if using sampleappkeystore.jks
            storePassword = "password" // UPDATE THIS if using sampleappkeystore.jks
        }
    }

    flavorDimensions += "default"
    productFlavors {
        create("stub") {
            isDefault = true
        }
        create("dev") {
        }
        create("prd") {
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("default")
            matchingFallbacks += listOf("tyroDebug")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
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
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("io.ktor:ktor-client-core:2.3.6")
    implementation("io.ktor:ktor-client-cio:2.3.6")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
    implementation("com.airbnb.android:lottie:6.0.1")
    implementation("com.airbnb.android:lottie-compose:6.0.1")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}