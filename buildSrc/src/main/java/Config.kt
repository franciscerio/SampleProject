const val kotlinVersion = "1.3.50"
const val androidPluginVersion = "3.3.2"
const val dexCountVersion = "0.8.2"
const val googlePlayVersion = "4.2.0"
const val crashlyticsGradlePluginVersion = "2.1.0"
const val navigationPluginVersion = "2.1.0-alpha04"

// Support
const val appCompatVersion = "1.1.0-alpha03"
const val constraintLayoutVersion = "1.1.2"
const val supportMaterialDesignVersion = "1.1.0-alpha04"

// Architecture Components
const val archCompVersion = "2.0.0-rc01"
const val archExtensionVersion = archCompVersion

// Core-KTX
const val coreKtxVersion = "1.0.2"

// Timber
const val timberVersion = "4.7.1"

// RxJava
const val rxJava2Version = "2.2.17"

// RxKotlin
const val rxKotlinVersion = "2.3.0"

// RxAndroid
const val rxAndroidVersion = "2.1.1"

// RxBinding
const val rxBindingXVersion = "3.0.0-alpha2"

// Dagger
const val daggerVersion = "2.20"

// Test dependency versions
const val junitVersion = "4.12"
const val testRunnerVersion = "1.1.0"
const val mockitoVersion = "2.25.1"
const val gsonVersion = "2.8.5"

object BuildPlugins {
    val androidPlugin = "com.android.tools.build:gradle:$androidPluginVersion"
    val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
}

object Android {
    // Manifest version information!
    private const val versionMajor = 0
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionBuild = 1 // bump for dogfood builds, public betas, etc.

    const val versionCode =
        versionMajor * 10000 + versionMinor * 1000 + versionPatch * 10 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val compileSdkVersion = 29
    const val targetSdkVersion = 29
    const val minSdkVersion = 26
    const val buildToolsVersion = "29.0.2"
}

object Libs {

    // Kotlin
    val kotlinStdlb = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"

    // Support
    val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
    val cardView = "androidx.cardview:cardview:$appCompatVersion"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    val supportDesign = "com.google.android.material:material:$supportMaterialDesignVersion"

    // Architecture Components
    val archExtensions = "androidx.lifecycle:lifecycle-extensions:$archExtensionVersion"
    val archExtensionsCompiler = "androidx.lifecycle:lifecycle-compiler:$archExtensionVersion"
    val room = "androidx.room:room-runtime:$archCompVersion"
    val roomRx = "androidx.room:room-rxjava2:$archCompVersion"
    val roomCompiler = "androidx.room:room-compiler:$archCompVersion"

    // Core-KTX
    val coreKTX = "androidx.core:core-ktx:$coreKtxVersion"

    // Timber
    val timber = "com.jakewharton.timber:timber:$timberVersion"

    // RxJava
    val rxjava2 = "io.reactivex.rxjava2:rxjava:$rxJava2Version"

    // RxKotlin
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:$rxKotlinVersion"

    // RxAndroid
    val rxandroid = "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"

    // RxBinding
    val rxbindingAndroidX = "com.jakewharton.rxbinding3:rxbinding:$rxBindingXVersion"
    val rxbindingAndroidXCore = "com.jakewharton.rxbinding3:rxbinding-core:$rxBindingXVersion"
    val rxbindingAndroidXAppCompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:$rxBindingXVersion"

    // Dagger
    val dagger = "com.google.dagger:dagger:$daggerVersion"
    val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    val daggerAndroid = "com.google.dagger:dagger-android:$daggerVersion"
    val daggerSupport = "com.google.dagger:dagger-android-support:$daggerVersion"
    val daggerProcessor = "com.google.dagger:dagger-android-processor:$daggerVersion"

}


object TestLibs {
    val junit = "junit:junit:$junitVersion"
    val testRunner = "androidx.test:runner:$testRunnerVersion"
    val mockito = "org.mockito:mockito-inline:$mockitoVersion"
    val archCoreTesting = "androidx.arch.core:core-testing:$archCompVersion"
}
