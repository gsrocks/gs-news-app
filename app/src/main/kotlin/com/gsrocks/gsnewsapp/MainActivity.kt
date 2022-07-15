package com.gsrocks.gsnewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsrocks.gsnewsapp.core.navigation.Routes
import com.gsrocks.gsnewsapp.core.navigation.navigate
import com.gsrocks.gsnewsapp.feature.news.presentation.NewsViewModel
import com.gsrocks.gsnewsapp.feature.news.presentation.article.ArticleScreen
import com.gsrocks.gsnewsapp.feature.news.presentation.home.HomeScreen
import com.gsrocks.gsnewsapp.ui.theme.GsNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainContent() }
    }
}

@Composable
fun MainContent() {
    GsNewsAppTheme {
        val navController = rememberNavController()

        val newsViewModel: NewsViewModel = hiltViewModel()

        NavHost(
            navController = navController,
            startDestination = Routes.HOME
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    viewModel = newsViewModel,
                    onNavigate = { navController.navigate(it) }
                )
            }
            composable(Routes.ARTICLE) {
                ArticleScreen(viewModel = newsViewModel)
            }
        }
    }
}
