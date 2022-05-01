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
        const val iconsExtended = "androidx.compose.material:material-icons-extended:$version"

        const val junit = "androidx.compose.ui:ui-test-junit4:$version"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
    }

    object Lifecycle {
        private const val version = "2.4.1"
        const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
    }

    object Coil {
        private const val version = "2.0.0-rc02"

        const val coil = "io.coil-kt:coil:$version"
        const val compose = "io.coil-kt:coil-compose:$version"
    }

    object Retrofit {
        private const val retrofitVersion = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"

        private const val moshiVersion = "1.13.0"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"

        private const val okHttpVersion = "4.9.3"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    }

    object Hilt {
        private const val version = "2.41"
        const val hiltAndroid = "com.google.dagger:hilt-android:$version"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$version"
        const val navigationCompose = "androidx.hilt:hilt-navigation:1.0.0"
    }

    object Plugins {
        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val kotlinAndroid = "org.jetbrains.kotlin.android"
        const val kotlinKapt = "kotlin-kapt"
        const val hiltAndroid = "dagger.hilt.android.plugin"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val androidExtJunit = "androidx.test.ext:junit:1.1.3"
        const val androidEspresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}