package com.gsrocks.news.feature.news.data.remote.dto

import com.squareup.moshi.Json

data class SourceDto(
    @field:Json(name = "id")
    val id: String? = null,
    @field:Json(name = "name")
    val name: String? = null
)