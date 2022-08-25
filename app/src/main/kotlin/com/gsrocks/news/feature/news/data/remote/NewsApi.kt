package com.gsrocks.news.feature.news.data.remote

import com.gsrocks.news.core.AppConstants
import com.gsrocks.news.feature.news.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = AppConstants.API_KEY
    ): NewsResponseDto

    @GET("everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = AppConstants.API_KEY
    ): NewsResponseDto

}