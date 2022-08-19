package com.gsrocks.gsnewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.gsrocks.gsnewsapp.navigation.AppNavigation
import com.gsrocks.gsnewsapp.ui.theme.GsNewsAppTheme
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
