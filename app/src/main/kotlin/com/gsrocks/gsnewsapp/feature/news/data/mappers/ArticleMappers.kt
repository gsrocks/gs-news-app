package com.gsrocks.gsnewsapp.feature.news.data.mappers

import com.gsrocks.gsnewsapp.feature.news.data.dto.ArticleDto
import com.gsrocks.gsnewsapp.feature.news.domain.model.Article
import com.gsrocks.gsnewsapp.feature.news.domain.model.Source

fun ArticleDto.mapToArticle(): Article {
    return Article(
        source = Source(
            id = source.id,
            name = source.name
        ),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}