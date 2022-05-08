package com.gsrocks.gsnewsapp.feature.news.presentation.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.gsrocks.gsnewsapp.feature.news.presentation.NewsViewModel

@Composable
fun ArticleScreen(
    viewModel: NewsViewModel,
    onNavigateUp: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.currentArticle != null) {
                val webViewState = rememberWebViewState(url = viewModel.currentArticle!!.url)
                WebView(state = webViewState)
                if (webViewState.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}