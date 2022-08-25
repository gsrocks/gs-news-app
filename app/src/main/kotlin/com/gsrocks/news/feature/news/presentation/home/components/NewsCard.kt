package com.gsrocks.news.feature.news.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gsrocks.news.core.utils.empty
import com.gsrocks.news.feature.news.domain.model.Article

@Composable
fun NewsCard(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .clip(RoundedCornerShape(4))
            .clickable(onClick = onClick)
    ) {
        Row {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(135.dp)
            ) {
                val imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .build()
                val painter = rememberAsyncImagePainter(model = imageRequest)
                Image(
                    painter = painter,
                    contentDescription = article.description,
                    modifier = Modifier.clip(
                        RoundedCornerShape(4)
                    )
                )
                Column {
                    Text(
                        text = article.source.name ?: String.empty,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = article.publishedAt ?: String.empty,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = article.title ?: String.empty,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.content ?: String.empty,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun NewsCardPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(4))
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .width(135.dp)
                )
                Column {
                    Text(
                        text = "...",
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "...",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "...",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "...",
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
