package com.gsrocks.gsnewsapp.feature.news.data.dto

import com.squareup.moshi.Json

data class NewsResponseDto(
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "totalResults")
    val totalResults: Int,
    @field:Json(name = "articles")
    val articles: List<ArticleDto>
)