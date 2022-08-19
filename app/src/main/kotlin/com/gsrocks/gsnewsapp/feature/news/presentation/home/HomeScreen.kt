package com.gsrocks.gsnewsapp.feature.news.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gsrocks.gsnewsapp.feature.news.presentation.NewsViewModel
import com.gsrocks.gsnewsapp.feature.news.presentation.home.tabs.BreakingNewsTab
import com.gsrocks.gsnewsapp.feature.news.presentation.home.tabs.FavouriteNewsTab
import com.gsrocks.gsnewsapp.feature.news.presentation.home.tabs.SearchNewsTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onNavigateToArticle: () -> Unit
) {
    val tabs = listOf(
        HomeScreenTab.BreakingNews,
        HomeScreenTab.SearchNews,
        HomeScreenTab.FavouriteNews
    )
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                navController = navController,
                tabs = tabs
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeScreenTab.BreakingNews.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(HomeScreenTab.BreakingNews.route) {
                BreakingNewsTab(
                    viewModel = viewModel,
                    onNavigateToArticle = { article ->
                        viewModel.setCurrentArticle(article)
                        onNavigateToArticle()
                    }
                )
            }
            composable(HomeScreenTab.SearchNews.route) {
                SearchNewsTab(
                    viewModel = viewModel,
                    onNavigateToArticle = { article ->
                        viewModel.setCurrentArticle(article)
                        onNavigateToArticle()
                    }
                )
            }
            composable(HomeScreenTab.FavouriteNews.route) {
                FavouriteNewsTab(
                    viewModel = viewModel,
                    onArticleClick = { article ->
                        viewModel.setCurrentArticle(article)
                        onNavigateToArticle()
                    }
                )
            }
        }
    }
}

@Composable
private fun HomeBottomNavigation(
    navController: NavController,
    tabs: List<HomeScreenTab>
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        tabs.forEach { tab ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = stringResource(tab.titleResId)
                    )
                },
                label = { Text(stringResource(tab.titleResId)) },
                selected = isSelected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
