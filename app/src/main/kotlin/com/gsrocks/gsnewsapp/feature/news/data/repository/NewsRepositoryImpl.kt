package com.gsrocks.gsnewsapp.feature.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gsrocks.gsnewsapp.feature.news.data.mappers.mapToArticle
import com.gsrocks.gsnewsapp.feature.news.data.remote.NewsApi
import com.gsrocks.gsnewsapp.feature.news.domain.model.Article
import com.gsrocks.gsnewsapp.feature.news.data.paging_source.BreakingNewsPagingSource
import com.gsrocks.gsnewsapp.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val newsApi: NewsApi
) : NewsRepository {

    override fun getBreakingNewsFlow(countryCode: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 80),
            pagingSourceFactory = { BreakingNewsPagingSource(newsApi, countryCode = countryCode) }
        ).flow
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