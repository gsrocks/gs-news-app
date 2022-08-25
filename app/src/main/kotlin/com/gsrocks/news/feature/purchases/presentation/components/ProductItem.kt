package com.gsrocks.news.feature.purchases.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gsrocks.news.core.utils.empty
import com.gsrocks.news.ui.theme.GsNewsAppTheme

@Composable
fun ProductItem(
    title: String,
    price: String,
    state: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(132.dp)
            .clip(shape = RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PriceLabel(
                label = String.empty,
                price = price
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "State: $state")
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    GsNewsAppTheme {
        ProductItem(
            title = "One time support 1",
            price = "$1.99",
            state = "PURCHASED"
        )
    }
}
