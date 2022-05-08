package com.gsrocks.gsnewsapp.feature.news.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gsrocks.gsnewsapp.R
import com.gsrocks.gsnewsapp.core.navigation.Routes
import com.gsrocks.gsnewsapp.core.navigation.UiEvent
import com.gsrocks.gsnewsapp.core.presentation.Resource
import com.gsrocks.gsnewsapp.core.presentation.composables.SearchBar
import com.gsrocks.gsnewsapp.feature.news.presentation.NewsViewModel

@Composable
fun SearchScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp, start = 8.dp, end = 8.dp)
    ) {
        val searchQuery = viewModel.searchQuery.collectAsState()
        val focusManager = LocalFocusManager.current
        SearchBar(
            value = searchQuery.value,
            onValueChange = {
                viewModel.onSearchQueryChange(it)
            },
            placeholder = stringResource(R.string.search_news),
            onSearch = { focusManager.clearFocus() }
        )

        when (val news = viewModel.searchedNews) {
            is Resource.Loading -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            is Resource.Failure -> Text(
                text = stringResource(R.string.failed_to_load_news),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            is Resource.Success -> {
                if (news.data.isEmpty()) {
                    if (searchQuery.value.isNotBlank()) {
                        Text(
                            text = stringResource(R.string.nothing_found),
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    LazyColumn {
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
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(onNavigate = {})
}