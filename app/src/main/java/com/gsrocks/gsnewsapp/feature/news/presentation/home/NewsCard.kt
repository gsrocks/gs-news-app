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
import com.gsrocks.gsnewsapp.ui.theme.GsNewsAppTheme

@Composable
fun NewsCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(4))
            .clickable(onClick = {})
            .background(color = MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Row {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                val imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/640px-Image_created_with_a_mobile_phone.png")
                    .build()
                val painter = rememberAsyncImagePainter(model = imageRequest)
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .width(135.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "News image",
                        modifier = Modifier.clip(
                            RoundedCornerShape(4)
                        )
                    )
                }
                Column {
                    Text(
                        text = "blabbermouth.net",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "12:34 04.12.2022",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "COREY TAYLOR Announces October 2022 U.K. Solo Tour",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "SLIPKNOT frontman Corey Taylor and his solo band will play on their first U.K. headline shows this fall. The dates kick off at London's legendary Palladium on October 17 and include a special gig on Friday, October 21 at the pre-party of the For The Love Of Horror convention at Manchester's BEC Arena. Direct support ...",
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    GsNewsAppTheme {
        NewsCard()
    }
}