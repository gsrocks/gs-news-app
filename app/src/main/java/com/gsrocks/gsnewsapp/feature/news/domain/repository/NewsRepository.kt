package com.gsrocks.gsnewsapp.feature.news.domain.repository

import com.gsrocks.gsnewsapp.feature.news.domain.model.Article

interface NewsRepository {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Result<List<Article>>

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<List<Article>>
}