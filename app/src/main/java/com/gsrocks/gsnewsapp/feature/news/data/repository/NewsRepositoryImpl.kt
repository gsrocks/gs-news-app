package com.gsrocks.gsnewsapp.feature.news.data.repository

import com.gsrocks.gsnewsapp.feature.news.data.mappers.mapToArticle
import com.gsrocks.gsnewsapp.feature.news.data.remote.NewsApi
import com.gsrocks.gsnewsapp.feature.news.domain.model.Article
import com.gsrocks.gsnewsapp.feature.news.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val newsApi: NewsApi
) : NewsRepository {

    override suspend fun getBreakingNews(
        countryCode: String,
        pageNumber: Int
    ): Result<List<Article>> {
        return try {
            val response = newsApi.getBreakingNews(countryCode, pageNumber)
                .articles
                .map { it.mapToArticle() }
            Result.success(response)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun searchNews(
        searchQuery: String,
        pageNumber: Int
    ): Result<List<Article>> {
        return try {
            val response = newsApi.searchForNews(searchQuery, pageNumber)
                .articles
                .map { it.mapToArticle() }
            Result.success(response)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}