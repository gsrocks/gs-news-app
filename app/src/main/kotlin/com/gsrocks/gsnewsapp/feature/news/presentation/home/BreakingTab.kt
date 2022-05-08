package com.gsrocks.gsnewsapp.feature.news.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gsrocks.gsnewsapp.R
import com.gsrocks.gsnewsapp.core.navigation.Routes
import com.gsrocks.gsnewsapp.core.navigation.UiEvent
import com.gsrocks.gsnewsapp.core.presentation.Resource
import com.gsrocks.gsnewsapp.feature.news.presentation.NewsViewModel

@Composable
fun BreakingScreen(
    viewModel: NewsViewModel,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    Box {
        when (val news = viewModel.breakingNews) {
            is Resource.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.TopCenter)
            )
            is Resource.Failure -> Text(
                text = stringResource(R.string.failed_to_load_news),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.TopCenter)
            )
            is Resource.Success -> LazyColumn {
                items(news.data) { article ->
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