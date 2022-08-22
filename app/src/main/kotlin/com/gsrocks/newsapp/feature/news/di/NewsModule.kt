package com.gsrocks.newsapp.feature.news.di

import com.gsrocks.newsapp.core.data.local.NewsDatabase
import com.gsrocks.newsapp.feature.news.data.local.NewsDao
import com.gsrocks.newsapp.feature.news.data.remote.NewsApi
import com.gsrocks.newsapp.feature.news.data.repository.NewsRepositoryImpl
import com.gsrocks.newsapp.feature.news.domain.repository.NewsRepository
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
    fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

    @Provides
    @Singleton
    fun provideNewsDao(db: NewsDatabase): NewsDao {
        return db.getNewsDao()
    }
}