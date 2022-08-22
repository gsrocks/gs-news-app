package com.gsrocks.newsapp.feature.news.data.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gsrocks.newsapp.feature.news.data.mappers.mapToArticle
import com.gsrocks.newsapp.feature.news.data.remote.NewsApi
import com.gsrocks.newsapp.feature.news.domain.model.Article

private const val NEWS_API_START_INDEX = 1

class BreakingNewsPagingSource(
    private val newsApi: NewsApi,
    private val countryCode: String
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: NEWS_API_START_INDEX
        return try {
            val result = newsApi.getBreakingNews(countryCode, position)
                .articles
                ?.map {
                    try {
                        it.mapToArticle()
                    } catch (e: Throwable) {
                        Log.e("BreakingNewsSource", "Error", e)
                        return LoadResult.Error(e)
                    }
                } ?: emptyList()
            LoadResult.Page(
                data = result,
                prevKey = when (position) {
                    NEWS_API_START_INDEX -> null
                    else -> position - 1
                },
                nextKey = when {
                    result.isEmpty() -> null
                    else -> position + 1
                }
            )
        } catch (e: Throwable) {
            //e.printStackTrace()
            Log.e("BreakingNewsSource", "Error", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        Log.d("BreakingNewsSource", "RefreshKey: ${state.anchorPosition}")
        return state.anchorPosition
    }
}