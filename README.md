# Tyro Tap to Pay SDK (Android)

## Requirements

To use this library you will need to target Android devices that:

- Have Android OS version:
  - **3.x.x:** [Android 10.0+ (API 29+)](https://developer.android.com/tools/releases/platforms#10)
  - **4.x.x:** [Android 12.0+ (API 31+)](https://developer.android.com/tools/releases/platforms#12)
- Have NFC capability
- Have internet access
- Have Google Mobile Services installed and pass CTS

## Limitations

**4.x.x releases**

- Dual screen POS devices can be supported, but a device must be certified before use can be enabled.
- Digital receipt functionality does not work on the stub or sandbox environment.

**3.x.x releases**

- The SDK does not support dual screen POS devices due to regulator standards.
  We are working with the regulator to enable this functionality in the future.
- Digital receipt functionality does not work on the stub or sandbox environment.

## GitHub access

Tyro Tap to Pay SDK is distributed via GitHub Packages, so you will need
a [GitHub account](https://github.com/join).
Follow [these steps](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic)
to create a Personal Access Token (PAT).
A 'classic' token with (at least) `public_repo` and `read:packages` scopes are sufficient.
Once you have created your PAT, you will need to reference it from your Android project.
You can do this using environment variables, as in the example below.

## Nexus access

The SDK also depends on two private Nexus repositories. The repository password is included in your
onboarding pack, or can be emailed securely after you contact Tyro. The repositories use the username
`tyro-tap-to-pay`. Like the GitHub token, reference the password from your Android project using an
environment variable named `TYRO_NEXUS_PASSWORD`.

## Add Tyro repositories in `settings.gradle`

Declare the GitHub Packages repository and both Nexus repositories in `settings.gradle.kts` (or
`settings.gradle`). All three are required.

<details open>
<summary><b>Kotlin</b> (default)</summary>

``` kotlin
val nexusPassword = System.getenv("TYRO_NEXUS_PASSWORD")

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/tyro/tyro-tap-to-pay-sdk-android")
            credentials {
                username = System.getenv("GITHUB_PACKAGES_USER")
                password = System.getenv("GITHUB_PACKAGES_TOKEN")
            }
        }
        maven {
            url = uri("https://nexus.softspace.com.my/nexus/content/repositories/ssfasstapsdk-group")
            credentials {
                username = "tyro-tap-to-pay"
                password = nexusPassword
            }
        }
        maven {
            url = uri("https://nexus.softspace.com.my/nexus/content/repositories/ssmpossdk-group")
            credentials {
                username = "tyro-tap-to-pay"
                password = nexusPassword
            }
        }
    }
}
```

</details>

<details>
<summary><b>Groovy</b></summary>

``` groovy
def nexusPassword = System.getenv("TYRO_NEXUS_PASSWORD")

dependencyResolutionManagement {
    repositories {
        maven {
            url 'https://maven.pkg.github.com/tyro/tyro-tap-to-pay-sdk-android'
            credentials {
                username System.getenv("GITHUB_PACKAGES_USER")
                password System.getenv("GITHUB_PACKAGES_TOKEN")
            }
        }
        maven {
            url 'https://nexus.softspace.com.my/nexus/content/repositories/ssfasstapsdk-group'
            credentials {
                username 'tyro-tap-to-pay'
                password nexusPassword
            }
        }
        maven {
            url 'https://nexus.softspace.com.my/nexus/content/repositories/ssmpossdk-group'
            credentials {
                username 'tyro-tap-to-pay'
                password nexusPassword
            }
        }
    }
}
```

</details>

## Add the Tyro dependency in `build.gradle`

> **Note:** 4.x.x uses a new `groupId` and new module names:
> `com.tyro.taptopay:taptopay-sdk:<latest-release-version>`

Select the SDK version and language. Kotlin is shown by default.

### 3.x.x (`com.tyro`)

<details open>
<summary><b>Kotlin</b> (default)</summary>

``` kotlin
dependencies {
    debugImplementation("com.tyro:tyro-tap-to-pay-sdk-debug:<latest-release-version>")
    releaseImplementation("com.tyro:tyro-tap-to-pay-sdk-release:<latest-release-version>")
}
```

</details>

<details>
<summary><b>Groovy</b></summary>

``` groovy
dependencies {
    debugImplementation "com.tyro:tyro-tap-to-pay-sdk-debug:<latest-release-version>"
    releaseImplementation "com.tyro:tyro-tap-to-pay-sdk-release:<latest-release-version>"
}
```

</details>

### 4.x.x (`com.tyro.taptopay`)

<details open>
<summary><b>Kotlin</b> (default)</summary>

``` kotlin
dependencies {
    debugImplementation("com.tyro.taptopay:taptopay-sdk-debug:<latest-release-version>")
    releaseImplementation("com.tyro.taptopay:taptopay-sdk-release:<latest-release-version>")
}
```

</details>

<details>
<summary><b>Groovy</b></summary>

``` groovy
dependencies {
    debugImplementation "com.tyro.taptopay:taptopay-sdk-debug:<latest-release-version>"
    releaseImplementation "com.tyro.taptopay:taptopay-sdk-release:<latest-release-version>"
}
```

</details>

## Debug vs Release builds

**3.x.x:** If your app is using the release build type, the SDK will require developer mode to be disabled
before you can initialise the SDK at runtime.

**4.x.x:** Release builds are used for production and will not work with the sandbox environment.
The `-debug` artifact will not work for production and only works with the sandbox environment.

## R8 Support

R8 [full mode](https://r8.googlesource.com/r8/+/refs/heads/master/compatibility-faq.md#r8-full-mode)
(`android.enableR8.fullMode`) can be enabled for SDK versions 3.2.x and 4.x.x.

```properties
android.enableR8.fullMode=true
```

Earlier versions of the SDK do not support R8 full mode. If you are using an earlier version, you may need
to include the following line in `gradle.properties`.

```properties
android.enableR8.fullMode=false
```

## Demo app

The demo app has three build flavors:

- stub
- dev
- prd

The app should run in `stub` flavor out of the box, which emulates success.

If you want to test the sample app in the development environment, you will need to:
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
