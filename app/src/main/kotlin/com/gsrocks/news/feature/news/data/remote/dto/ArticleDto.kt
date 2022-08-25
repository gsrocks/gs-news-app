package com.gsrocks.news.feature.news.data.remote.dto

import com.squareup.moshi.Json

data class ArticleDto(
    @field:Json(name = "source")
    val source: SourceDto? = null,
    @field:Json(name = "author")
    val author: String? = null,
    @field:Json(name = "title")
    val title: String? = null,
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "url")
    val url: String? = null,
    @field:Json(name = "urlToImage")
    val urlToImage: String? = null,
    @field:Json(name = "publishedAt")
    val publishedAt: String? = null,
    @field:Json(name = "content")
    val content: String? = null
)