# Tyro Tap to Pay SDK (Android)

## Requirements

To use this library you will need to target Android devices that:

- Have [Android 11.0+ (API 30+)](https://developer.android.com/tools/releases/platforms#11)
- Have NFC capability
- Have internet access
- Have Google Mobile Services installed and pass CTS

## Limitation

- Currently the SDK does not support dual screen POS devices due to regulator standards.
  We are working with the regulator to enable this functionality in the future.
- Digital receipt functionality does not work on the stub or sandbox environment

## Github access

Tyro Tap To Pay SDK is distributed via Github Packages, so you will need
a [Github account](https://github.com/join).
Follow [these steps](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic)
to create a Personal Access Token (PAT).
A 'classic' token with (at least) `public_repo` scope is sufficient.
Once you have created your PAT, you will need to reference it from your Android project.
You can do this using environment variables, as in the example below.

## Add Tyro repository in `settings.gradle`

### Kotlin

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

### Groovy

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

## Add the Tyro dependency in`build.gradle`

### Kotlin

``` kotlin

dependencies {
    // ...
    debugImplementation("com.tyro:tyro-tap-to-pay-sdk-debug:latest.release")
    releaseImplementation("com.tyro:tyro-tap-to-pay-sdk-release:latest.release")
}
```

### Groovy

``` groovy

dependencies {
    // ...
    debugImplementation "com.tyro:tyro-tap-to-pay-sdk-debug:latest.release"
    releaseImplementation "com.tyro:tyro-tap-to-pay-sdk-release:latest.release"
}
```

## Debug vs Release builds

If your app is using the release built type, the SDK will require the developer mode to be disabled
before you can init the SDK at runtime.

## R8 Support

Tyro Tap to Pay SDK does not currently
support [R8 full mode](https://r8.googlesource.com/r8/+/refs/heads/master/compatibility-faq.md#r8-full-mode).
You may need to include the following line in `gradle.properties`.

```properties
android.enableR8.fullMode=false
```

## Demo app

The demo app has three build flavors:

- stub
- dev
- prd

The app should run in `stub` flavor out of the box which emulates success.

If you want get to test the sample app in the development environment, you will need to:
- Obtain the keystore file `sampleappkeystore.jks` and its passwords from Tyro, then place the file in `SampleApp/app`.
- Update `signingConfigs` (`storeFile`, `storePassword`, `keyAlias`, `keyPassword`) to use the provided keystore.

If you want to run your own app against development or production environments, you will need to:
- Change the `applicationId` in `build.gradle` to your own
- Include [your own keystore](https://developer.android.com/studio/publish/app-signing#generate-key), and update the `signingConfigs` part of `build.gradle` with your keystore details
- Export a public certificate from your keystore and provide it to Tyro along with your `applicationId`
    - Run the following to generate your public cert: `keytool -export -alias key0 -keystore yourkeystore.jks -rfc -file publiccert.pem`
    - Make sure the alias is the same as the one you used to generate the keystore
- Implement authentication with Tyro on your own server

See our [documentation](https://docs.connect.tyro.com/pos/embedded-payments/android/integrate-sdk/) for more
detail on these requirements.

## Need help?

Reach out to the `Connect Support Team`
at [connect-support@tyro.com](mailto:connect-support@tyro.com)
