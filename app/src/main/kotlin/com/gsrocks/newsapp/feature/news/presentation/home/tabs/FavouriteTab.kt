package com.gsrocks.newsapp.feature.news.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gsrocks.newsapp.R
import com.gsrocks.newsapp.core.navigation.Routes
import com.gsrocks.newsapp.core.navigation.UiEvent
import com.gsrocks.newsapp.feature.news.presentation.NewsViewModel

@Composable
fun FavouriteScreen(
    viewModel: NewsViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val news by viewModel.getFavouriteArticles().collectAsState(emptyList())
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (news.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.no_favourite_items)
            )
        } else {
            LazyColumn {
                items(news) { article ->
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
    }
}
