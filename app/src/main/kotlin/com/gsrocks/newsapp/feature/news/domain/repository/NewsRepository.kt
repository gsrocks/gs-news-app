package com.gsrocks.newsapp.feature.news.domain.repository

import androidx.paging.PagingData
import com.gsrocks.newsapp.feature.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getBreakingNewsFlow(countryCode: String): Flow<PagingData<Article>>

    suspend fun searchNews(searchQuery: String, pageNumber: Int): Result<List<Article>>

    suspend fun insertOrUpdateFavouriteArticle(article: Article)

    suspend fun deleteFavouriteArticle(article: Article)

    fun getFavouriteArticles(): Flow<List<Article>>
}