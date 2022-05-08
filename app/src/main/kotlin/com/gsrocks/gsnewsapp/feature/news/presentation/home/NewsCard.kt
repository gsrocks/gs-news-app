package com.gsrocks.gsnewsapp.feature.news.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gsrocks.gsnewsapp.core.utils.empty
import com.gsrocks.gsnewsapp.feature.news.domain.model.Article
import com.gsrocks.gsnewsapp.ui.theme.GsNewsAppTheme

@Composable
fun NewsCard(
    article: Article,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(4))
            .clickable(onClick = onClick)
            .background(color = MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Row {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                val imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .build()
                val painter = rememberAsyncImagePainter(model = imageRequest)
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .width(135.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = article.description,
                        modifier = Modifier.clip(
                            RoundedCornerShape(4)
                        )
                    )
                }
                Column {
                    Text(
                        text = article.source.name,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = article.publishedAt,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.content ?: String.empty,
                    fontSize = 15.sp,
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
            .background(color = MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Row {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .width(135.dp)
                ) {
                }
                Column {
                    Text(
                        text = "...",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "...",
                        fontSize = 16.sp,
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
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
