package com.gsrocks.gsnewsapp.feature.news.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gsrocks.gsnewsapp.core.navigation.UiEvent
import com.gsrocks.gsnewsapp.feature.news.presentation.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val items = listOf("Breaking", "Search", "Favourite")
    val icons = listOf(Icons.Default.Feed, Icons.Default.Search, Icons.Filled.Favorite)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colors.surface
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = null) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colors.secondary
                        )
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(bottom = 80.dp)) {
            when (selectedItem) {
                0 -> BreakingScreen(
                    pagingFlow = viewModel.breakingNewsFlow,
                    onSetCurrentArticle = viewModel::setCurrentArticle,
                    onNavigate = onNavigate
                )
                1 -> SearchScreen(viewModel, onNavigate)
                2 -> FavouriteScreen(viewModel, onNavigate)
                else -> UnknownScree()
            }
        }
    }
}

@Composable
fun UnknownScree() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Unknown tab")
    }
}