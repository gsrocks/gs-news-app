package com.gsrocks.news.feature.news.presentation.home.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gsrocks.news.R
import com.gsrocks.news.feature.news.domain.model.Article
import com.gsrocks.news.feature.news.presentation.NewsViewModel
import com.gsrocks.news.feature.news.presentation.home.components.NewsCard
import com.gsrocks.news.feature.news.presentation.home.components.NewsCardPlaceholder

@Composable
fun BreakingNewsTab(
    viewModel: NewsViewModel,
    onNavigateToArticle: (Article) -> Unit
) {
    val listState = rememberLazyListState()
    val news = viewModel.breakingNewsFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
    ) {
        items(news) { article ->
            if (article != null) {
                NewsCard(
                    article = article,
                    onClick = { onNavigateToArticle(article) },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            } else {
                NewsCardPlaceholder()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (news.loadState.refresh) {
            is LoadState.Loading -> CircularProgressIndicator()
            is LoadState.Error -> Text(text = stringResource(R.string.failed_to_load_news))
            else -> {}
        }
    }
}
