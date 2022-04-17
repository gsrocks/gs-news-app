object Dependencies {
    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
    }

    object Compose {
        const val version = "1.1.1"

        const val ui = "androidx.compose.ui:ui:$version"
        const val material = "androidx.compose.material:material:$version"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val material3 = "androidx.compose.material3:material3:1.0.0-alpha09"
        const val activityCompose = "androidx.activity:activity-compose:1.4.0"

        const val junit = "androidx.compose.ui:ui-test-junit4:$version"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
    }

    object Lifecycle {
        private const val version = "2.4.1"
        const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
    }

    object Plugins {
        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val kotlinAndroid = "org.jetbrains.kotlin.android"
        const val kotlinKapt = "kotlin-kapt"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val androidExtJunit = "androidx.test.ext:junit:1.1.3"
        const val androidEspresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}