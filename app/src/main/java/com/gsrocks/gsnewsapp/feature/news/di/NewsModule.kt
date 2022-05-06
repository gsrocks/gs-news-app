package com.gsrocks.gsnewsapp.feature.news.di

import com.gsrocks.gsnewsapp.feature.news.data.remote.NewsApi
import com.gsrocks.gsnewsapp.feature.news.data.repository.NewsRepositoryImpl
import com.gsrocks.gsnewsapp.feature.news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }
}