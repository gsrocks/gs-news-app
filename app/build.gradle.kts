import java.util.Properties
import java.io.FileInputStream

plugins {
    id(Dependencies.Plugins.androidApplication)
    id(Dependencies.Plugins.kotlinAndroid)
    id(Dependencies.Plugins.kotlinKapt)
    id(Dependencies.Plugins.hiltAndroid)
    id(Dependencies.Plugins.ksp) version "1.7.10-1.0.6"
}

val keystoreProperties = Properties().apply {
    load(FileInputStream(rootProject.file("signing/keystore.properties")))
}
val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.gsrocks.news"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "BASE64_ENCODED_PUBLIC_KEY",
            "\"${localProperties.getProperty("base64EncodedPublicKey")}\""
        )
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("release")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersoin
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)

    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.preview)
    debugImplementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.activityCompose)
    implementation(Dependencies.Compose.iconsExtended)

    implementation(Dependencies.Ui.viewPager)

    implementation(Dependencies.Lifecycle.lifecycleKtx)
    implementation(Dependencies.Lifecycle.viewModelCompose)

    implementation(Dependencies.Coil.compose)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.okHttp)
    implementation(Dependencies.Retrofit.loggingInterceptor)
    implementation(Dependencies.Retrofit.moshiConverter)
    ksp(Dependencies.Retrofit.moshiCodegen)

    implementation(Dependencies.Hilt.hiltAndroid)
    kapt(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.navigationCompose)

    implementation(Dependencies.Coroutines.coroutines)

    implementation(Dependencies.Ui.webView)

    implementation(Dependencies.Paging.paging)

    implementation(Dependencies.Room.room)
    ksp(Dependencies.Room.compiler)
    implementation(Dependencies.Room.ktx)

    implementation(Dependencies.Billing.billing)
    implementation(Dependencies.Billing.ktx)

    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.androidExtJunit)
    androidTestImplementation(Dependencies.Test.androidEspresso)
    androidTestImplementation(Dependencies.Compose.junit)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}