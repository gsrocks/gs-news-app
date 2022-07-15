package com.gsrocks.newsapp.feature.news.data.remote.dto

import com.squareup.moshi.Json

data class ArticleDto(
    @field:Json(name = "source")
    val source: SourceDto,
    @field:Json(name = "author")
    val author: String?,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "urlToImage")
    val urlToImage: String?,
    @field:Json(name = "publishedAt")
    val publishedAt: String,
    @field:Json(name = "content")
    val content: String?
)