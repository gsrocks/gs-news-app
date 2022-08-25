package com.gsrocks.news.feature.news.presentation.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.gsrocks.news.R
import com.gsrocks.news.feature.news.presentation.NewsViewModel

@Composable
fun ArticleScreen(viewModel: NewsViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.currentArticle?.let {
                        viewModel.saveFavouriteArticle(it)
                    }
                }
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.save)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.currentArticle?.url != null) {
                val webViewState = rememberWebViewState(url = viewModel.currentArticle!!.url!!)
                WebView(state = webViewState)
                if (webViewState.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
