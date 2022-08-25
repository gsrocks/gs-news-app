package com.gsrocks.news.feature.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gsrocks.news.feature.news.data.local.NewsDao
import com.gsrocks.news.feature.news.data.mappers.mapToArticle
import com.gsrocks.news.feature.news.data.mappers.mapToDbEntity
import com.gsrocks.news.feature.news.data.remote.NewsApi
import com.gsrocks.news.feature.news.domain.model.Article
import com.gsrocks.news.feature.news.data.paging_source.BreakingNewsPagingSource
import com.gsrocks.news.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
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
                ?.map { it.mapToArticle() } ?: emptyList()
            Result.success(response)
        } catch (e: Throwable) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertOrUpdateFavouriteArticle(article: Article) {
        newsDao.insertOrUpdateArticle(article.mapToDbEntity())
    }

    override suspend fun deleteFavouriteArticle(article: Article) {
        newsDao.deleteArticle(article.mapToDbEntity())
    }

    override fun getFavouriteArticles(): Flow<List<Article>> {
        return newsDao.getFavouriteArticles().map {
            it.map { articleEntity -> articleEntity.mapToArticle() }
        }
    }
}
