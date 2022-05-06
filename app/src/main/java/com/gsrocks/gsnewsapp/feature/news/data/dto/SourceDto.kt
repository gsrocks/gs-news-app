package com.gsrocks.gsnewsapp.feature.news.data.dto

import com.squareup.moshi.Json

data class SourceDto(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "name")
    val name: String
)