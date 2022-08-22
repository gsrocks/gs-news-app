package com.gsrocks.newsapp.feature.news.presentation.home.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gsrocks.newsapp.R
import com.gsrocks.newsapp.core.presentation.Resource
import com.gsrocks.newsapp.core.presentation.composables.SearchBar
import com.gsrocks.newsapp.feature.news.domain.model.Article
import com.gsrocks.newsapp.feature.news.presentation.NewsViewModel
import com.gsrocks.newsapp.feature.news.presentation.home.components.NewsCard

@Composable
fun SearchNewsTab(
    viewModel: NewsViewModel,
    onNavigateToArticle: (Article) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val searchQuery = viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        SearchBar(
            value = searchQuery.value,
            onValueChange = {
                viewModel.onSearchQueryChange(it)
            },
            placeholder = stringResource(R.string.search_news),
            onSearch = { focusManager.clearFocus() },
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        when (val news = viewModel.searchedNews) {
            is Resource.Loading -> CircularProgressIndicator()
            is Resource.Failure -> Text(text = stringResource(R.string.failed_to_load_news))
            is Resource.Success -> {
                if (news.data.isEmpty()) {
                    if (searchQuery.value.isNotBlank()) {
                        Text(text = stringResource(R.string.nothing_found))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(news.data) { article ->
                            NewsCard(
                                article = article,
                                onClick = { onNavigateToArticle(article) },
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
