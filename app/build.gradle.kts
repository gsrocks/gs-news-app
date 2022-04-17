plugins {
    id(Dependencies.Plugins.androidApplication)
    id(Dependencies.Plugins.kotlinAndroid)
    id(Dependencies.Plugins.kotlinKapt)
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.gsrocks.gsnewsapp"
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
        kotlinCompilerExtensionVersion = Dependencies.Compose.version
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
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.activityCompose)

    implementation(Dependencies.Lifecycle.lifecycleKtx)
    implementation(Dependencies.Lifecycle.viewModelCompose)

    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.androidExtJunit)
    androidTestImplementation(Dependencies.Test.androidEspresso)
    androidTestImplementation(Dependencies.Compose.junit)
    debugImplementation(Dependencies.Compose.uiTooling)
}