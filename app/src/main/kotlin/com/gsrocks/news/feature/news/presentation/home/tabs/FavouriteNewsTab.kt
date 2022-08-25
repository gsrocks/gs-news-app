package com.gsrocks.news.feature.news.presentation.home.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gsrocks.news.R
import com.gsrocks.news.feature.news.domain.model.Article
import com.gsrocks.news.feature.news.presentation.NewsViewModel
import com.gsrocks.news.feature.news.presentation.home.components.NewsItem

@Composable
fun FavouriteNewsTab(
    viewModel: NewsViewModel,
    onArticleClick: (Article) -> Unit
) {
    val news by viewModel.watchFavouriteArticles().collectAsState(initial = emptyList())

    if (!viewModel.isSubscriptionActive) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "No access",
                modifier = Modifier.size(72.dp)
            )
        }
    } else {
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
                        NewsItem(
                            article = article,
                            onClick = { onArticleClick(article) },
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
