package com.gsrocks.news.feature.news.data.mappers

import com.gsrocks.news.feature.news.data.remote.dto.ArticleDto
import com.gsrocks.news.feature.news.data.local.entity.ArticleEntity
import com.gsrocks.news.feature.news.domain.model.Article
import com.gsrocks.news.feature.news.domain.model.Source

fun ArticleDto.mapToArticle(): Article {
    return Article(
        source = Source(
            id = source?.id,
            name = source?.name
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

fun Article.mapToDbEntity(): ArticleEntity {
    return ArticleEntity(
        sourceId = source.id,
        sourceName = source.name,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun ArticleEntity.mapToArticle(): Article {
    return Article(
        source = Source(
            id = sourceId,
            name = sourceName
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
