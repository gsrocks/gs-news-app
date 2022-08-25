package com.gsrocks.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gsrocks.news.navigation.AppNavigation
import com.gsrocks.news.ui.theme.GsNewsAppTheme
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
