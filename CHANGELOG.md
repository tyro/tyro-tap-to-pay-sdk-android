
# Change Log
All notable changes to this project will be documented in this file.
 
The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [2.0.2] - 2025-06-17

### Fixed
- Fixed known UI issue on transaction flow, allowing for seamless full-screen appearance

## [2.0.1] - 2025-04-08

### Added
- Added security checks for split screen, screen capturing and screen sharing in presentation mode
### Changed
- Enabled edge-to-edge display
- Changed haptic feedback vibration pattern when presented card is read successfully
### Fixed
- Fixed known UI issues

## [2.0.0] - 2025-01-24

### Added
- Added support for **Light**, **Dark**, and **System Default** themes
- Added new option fields:
    - `themeMode`: Allows users to set the desired theme (Light/Dark/System)
    - `hapticFeedbackEnabled`: Enables or disables haptic feedback for the SDK
- Added a new function that enables users to dynamically toggle haptic feedback on or off during runtime
### Changed
- Renamed all instances of "Refund Password" to "Refund Passcode"
- Redesigned the UI for Admin Settings and Refund Passcode screens
- Updated SDK to Kotlin 2.0.21
- Updated dependency: Ktor 3.0.3

## [1.0.6] - 2024-11-22

### Changed
- Upgraded to latest material3 version (androidx.compose.material3:material3:1.3.1)

## [1.0.5] - 2024-11-19

### Fixed
- Fixes Apple Pay refund processing with Visa card
- Fixes large amount display format in tap screen
- Fixes transaction result amount and surcharge format in Stub mode

## [1.0.4] - 2024-08-15

### Changed
- Updated Compose library to v1.6.8 [2024.06.00]

## [1.0.3] - 2024-07-17

### Fixed
- Fixed crash reporting library bug

## [1.0.2] - 2024-07-08

### Added
- Adds the production configuration.
- Adds crash reporting for SDK related crashes.
### Changed
- Improvements to use newer Android APIs.
- Removes deprecated functions

## [1.0.1] - 2024-07-02

### Fixed
- Fixes screen size check for tablets when landscape orientation is set.


## [1.0.0] - 2024-06-24
BREAKING change to TyroEnv classes. PosInfo can no longer be passed to the constructor, use sdk.setPosInfo() or set in the TransactionRequest instead.
### Added
- Adds sdk.setPosInfo() function and allows posInfo to be set in the TransactionRequest.
### Changed
- BREAKING change to TyroEnv classes. PosInfo can no longer be passed to the constructor, use sdk.setPosInfo() or set in the TransactionRequest instead.

## [0.41.0] - 2024-06-19
### Added
- Adds screen orientation configuration when initialising the sdk. Landscape and other options is now supported for tablets.
### Changed
- Deprecated sdk.createInstance(envConfig) static function. Must use new function that requires 3 params. Function must be called in onCreate() of application.
### Fixed
- Small fixes

## [0.39.0] - 2024-06-17
### Added
- Adds surcharge features to purchases such as surcharge rates modal.
### Changed
- Minor UI updates
- Updates third party libraries
### Fixed
- Small fixes

## [0.36.0] - 2024-06-07
### Changed
- Removes surcharge option for updateAdminSettings().
### Fixed
- Small fixes
## [0.35.0] - 2024-06-05
### Added
- Adds updateAdminSettings() function which allows your POS admin to set a password.
- Refunds now require a password to access refunds. The password must be set via the updateAdminSettings() function.
### Changed
- Updates third party libraries
### Fixed
- Small fixes
 
## [0.34.0] - 2024-05-27
### Changed
- Updates third party libraries
### Fixed
- Fixes to make SDK more stable