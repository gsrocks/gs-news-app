package com.gsrocks.gsnewsapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsrocks.gsnewsapp.feature.news.presentation.NewsViewModel
import com.gsrocks.gsnewsapp.feature.news.presentation.article.ArticleScreen
import com.gsrocks.gsnewsapp.feature.news.presentation.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val newsViewModel: NewsViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Route.HOME
    ) {
        composable(Route.HOME) {
            HomeScreen(
                viewModel = newsViewModel,
                onNavigateToArticle = {
                    navController.navigate(Route.ARTICLE)
                }
            )
        }
        composable(Route.ARTICLE) {
            ArticleScreen(
                viewModel = newsViewModel
            )
        }
    }
}
