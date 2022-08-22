package com.gsrocks.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gsrocks.newsapp.feature.news.presentation.NewsViewModel
import com.gsrocks.newsapp.feature.news.presentation.article.ArticleScreen
import com.gsrocks.newsapp.feature.news.presentation.home.HomeScreen
import com.gsrocks.newsapp.feature.purchases.presentation.subscriptions.SubscriptionsScreen

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
        composable(Route.SUBSCRIPTIONS) {
            SubscriptionsScreen()
        }
        composable(Route.ONE_TIME_PRODUCTS) {

        }
    }
}
