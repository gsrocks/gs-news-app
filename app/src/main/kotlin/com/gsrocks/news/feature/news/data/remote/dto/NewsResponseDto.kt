package com.gsrocks.news.feature.news.data.remote.dto

import com.squareup.moshi.Json

data class NewsResponseDto(
    @field:Json(name = "status")
    val status: String? = null,
    @field:Json(name = "totalResults")
    val totalResults: Int? = null,
    @field:Json(name = "articles")
    val articles: List<ArticleDto>? = null
)