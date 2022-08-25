package com.gsrocks.news.feature.news.presentation.home.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PurchasesTab(
    onNavigateToSubscriptions: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "One-time products")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToSubscriptions) {
            Text(text = "Subscriptions")
        }
    }
}
