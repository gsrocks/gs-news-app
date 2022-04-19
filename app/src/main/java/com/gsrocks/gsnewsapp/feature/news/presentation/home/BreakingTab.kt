package com.gsrocks.gsnewsapp.feature.news.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BreakingScreen() {
    LazyColumn {
        items(12) {
            Box(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
            ) {
                NewsCard()
            }
        }
    }
}