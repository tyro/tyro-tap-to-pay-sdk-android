# Tyro Tap to Pay SDK (Android)

To use this library you will need to target Android devices that:
- Have [Android 11.0+ (API 30+)](https://developer.android.com/tools/releases/platforms#11)
- Have NFC capability
- Have internet access
- Have Google Mobile Services installed and pass CTS

### Github access

Tyro Tap To Pay SDK is distributed via Github Packages, so you will need a [Github account](https://github.com/join).
Follow [these steps](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic) to create a Personal Access Token (PAT).
A 'classic' token with (at least) `public_repo` scope is sufficient.
Once you have created your PAT, you will need to reference it from your Android project.
You can do this using environment variables, as in the example below.

### In settings.gradle add the Tyro repository:

#### Kotlin
``` kotlin
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/tyro/tyro-tap-to-pay-sdk-android")
            credentials {
                username = System.getenv("GITHUB_PACKAGES_USER")
                password = System.getenv("GITHUB_PACKAGES_TOKEN")
            }
        }
    }
}
```

#### Groovy
``` groovy
dependencyResolutionManagement {
    repositories {
        maven {
            url 'https://maven.pkg.github.com/tyro/tyro-tap-to-pay-sdk-android'
            credentials {
                username System.getenv("GITHUB_PACKAGES_USER")
                password System.getenv("GITHUB_PACKAGES_TOKEN")
            }
        }
    }
}
```

### In build.gradle add the Tyro dependency:

#### Kotlin
``` kotlin

dependencies {
    // ...
    debugImplementation("com.tyro:tyro-tap-to-pay-sdk-debug:latest.release")
    releaseImplementation("com.tyro:tyro-tap-to-pay-sdk-release:latest.release")
}
```

#### Groovy
``` groovy

dependencies {
    // ...
    debugImplementation "com.tyro:tyro-tap-to-pay-sdk-debug:latest.release"
    releaseImplementation "com.tyro:tyro-tap-to-pay-sdk-release:latest.release"
}
```

### Accept tyroDebug build type

Tyro publishes two dependencies:
- `tyro-tap-to-pay-sdk-debug`
- `tyro-tap-to-pay-sdk-release`

The `release` dependency includes some additional security checks.
Most notable of these is that it requires [on-device developer mode](https://developer.android.com/studio/debug/dev-options) to be **disabled**.

The `debug` build published by Tyro does not have this requirement, making testing easier.
It is, however, obfuscated and non-debuggable.
You will need to configure your `debug` build type to accept `tyroDebug` as a fallback.

``` kotlin
android {
    // ...
    buildTypes {
        // ...
        debug {
            // ...
            matchingFallbacks += listOf("tyroDebug")
        }
    }
}
```

``` groovy
android {
    // ...
    buildTypes {
        // ...
        debug {
            // ...
            matchingFallbacks.add("tyroDebug")
        }
    }
}
```

### R8 Support

Tyro Tap to Pay SDK does not currently support [R8 full mode](https://r8.googlesource.com/r8/+/refs/heads/master/compatibility-faq.md#r8-full-mode).
You may need to include the following line in `gradle.properties`.

```properties
android.enableR8.fullMode=false
```

### Demonstration App

The demo app has three build flavors:

- stub
- dev
- prd

The app should run in `stub` flavor out of the box which emulates success.

If you want get to test the sample app in the development environment, you will need to:
- Obtain the keystore `sampleappkeystore.jks` file and password from Tyro and place the keystore in the `SampleApp/app` folder.
- Update the `signingConfigs` ,`storeFile`,`keyPassword`,`storePassword` part of `app/build.gradle.kts` with the keystore and password provided to you.

If you want to run your own App against development or production environments, you will need to:
- Change the `applicationId` in `build.gradle` to your own
- Include [your own keystore](https://developer.android.com/studio/publish/app-signing#generate-key), and update the `signingConfigs` part of `build.gradle` with your keystore details
- Export a public certificate from your keystore and provide it to Tyro along with your `applicationId`
    - Run the following to generate your public cert: `keytool -export -alias key0 -keystore yourkeystore.jks -rfc -file publiccert.pem`
    - Make sure the alias is the same as the one you used to generate the keystore
- Implement authentication with Tyro on your own server

See our [documentation preview](https://docs.connect.tyro.com/pos/embedded-payments/android/integrate-sdk/) for more 
detail on these requirements.

### Digital Receipt
Please note: digital receipt functionality does not work on the sandbox environment

## Need help?
Reach out to the `Connect Support Team` at [connect-support@tyro.com](mailto:connect-support@tyro.com)