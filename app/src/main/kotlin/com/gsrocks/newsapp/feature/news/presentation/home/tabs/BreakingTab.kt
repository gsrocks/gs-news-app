package com.gsrocks.newsapp.feature.news.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gsrocks.newsapp.R
import com.gsrocks.newsapp.core.navigation.Routes
import com.gsrocks.newsapp.core.navigation.UiEvent
import com.gsrocks.newsapp.feature.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Composable
fun BreakingScreen(
    pagingFlow: Flow<PagingData<Article>>,
    onSetCurrentArticle: (Article) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val news = pagingFlow.collectAsLazyPagingItems()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (news.loadState.refresh) {
            is LoadState.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.Center)
            )
            is LoadState.Error -> Text(
                text = stringResource(R.string.failed_to_load_news),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.Center)
            )
            is LoadState.NotLoading -> LazyColumn {
                items(news) { article ->
                    Box(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 4.dp
                        )
                    ) {
                        if (article != null) {
                            NewsCard(
                                article,
                                onClick = {
                                    onSetCurrentArticle(article)
                                    onNavigate(UiEvent.Navigate(Routes.ARTICLE))
                                }
                            )
                        } else {
                            NewsCardPlaceholder()
                        }
                    }
                }
            }
        }
    }
}