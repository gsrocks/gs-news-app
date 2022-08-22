package com.gsrocks.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gsrocks.newsapp.navigation.AppNavigation
import com.gsrocks.newsapp.ui.theme.GsNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GsNewsAppTheme {
                AppNavigation()
            }
        }
    }
}
