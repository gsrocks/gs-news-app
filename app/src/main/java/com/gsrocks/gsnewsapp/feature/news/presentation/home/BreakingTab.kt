package com.gsrocks.gsnewsapp.feature.news.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gsrocks.gsnewsapp.core.navigation.Routes
import com.gsrocks.gsnewsapp.core.navigation.UiEvent
import com.gsrocks.gsnewsapp.feature.news.presentation.NewsViewModel

@Composable
fun BreakingScreen(
    viewModel: NewsViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    LazyColumn {
        items(viewModel.breakingNews) { article ->
            Box(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
            ) {
                NewsCard(
                    article,
                    onClick = {
                        viewModel.setCurrentArticle(article)
                        onNavigate(UiEvent.Navigate(Routes.ARTICLE))
                    }
                )
            }
        }
    }
}