package com.gsrocks.newsapp.feature.news.presentation.home.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gsrocks.newsapp.R
import com.gsrocks.newsapp.feature.news.domain.model.Article
import com.gsrocks.newsapp.feature.news.presentation.NewsViewModel
import com.gsrocks.newsapp.feature.news.presentation.home.components.NewsCard

@Composable
fun FavouriteNewsTab(
    viewModel: NewsViewModel,
    onArticleClick: (Article) -> Unit
) {
    val news by viewModel.watchFavouriteArticles().collectAsState(initial = emptyList())

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (news.isEmpty()) {
            Text(text = stringResource(R.string.nothing_found))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
            ) {
                items(news) { article ->
                    NewsCard(
                        article = article,
                        onClick = { onArticleClick(article) },
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}
