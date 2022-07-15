plugins {
    id(Dependencies.Plugins.androidApplication)
    id(Dependencies.Plugins.kotlinAndroid)
    id(Dependencies.Plugins.kotlinKapt)
    id(Dependencies.Plugins.hiltAndroid)
    id(Dependencies.Plugins.ksp) version "1.7.0-1.0.6"
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.gsrocks.newsapp"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersion
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
    debugImplementation(Dependencies.Compose.preview)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.activityCompose)
    implementation(Dependencies.Compose.iconsExtended)

    implementation(Dependencies.Lifecycle.lifecycleKtx)
    implementation(Dependencies.Lifecycle.viewModelCompose)

    implementation(Dependencies.Coil.compose)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.okHttp)
    implementation(Dependencies.Retrofit.loggingInterceptor)
    implementation(Dependencies.Retrofit.moshiConverter)
    kapt(Dependencies.Retrofit.moshiCodegen)

    implementation(Dependencies.Hilt.hiltAndroid)
    kapt(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.navigationCompose)

    implementation(Dependencies.Coroutines.coroutines)

    implementation(Dependencies.Ui.webView)

    implementation(Dependencies.Paging.paging)

    implementation(Dependencies.Room.room)
    ksp(Dependencies.Room.compiler)
    implementation(Dependencies.Room.ktx)

    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.androidExtJunit)
    androidTestImplementation(Dependencies.Test.androidEspresso)
    androidTestImplementation(Dependencies.Compose.junit)
    debugImplementation(Dependencies.Compose.uiTooling)
}