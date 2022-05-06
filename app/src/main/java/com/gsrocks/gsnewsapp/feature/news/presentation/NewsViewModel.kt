package com.gsrocks.gsnewsapp.feature.news.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.gsnewsapp.feature.news.domain.model.Article
import com.gsrocks.gsnewsapp.feature.news.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    var breakingNews by mutableStateOf<List<Article>>(emptyList())
        private set

    var currentArticle: Article? = null
        private set

    init {
        getBreakingNews("us", 1)
    }

    fun getBreakingNews(countryCode: String, pageNumber: Int) = viewModelScope.launch {
        val result = newsRepository.getBreakingNews(countryCode, pageNumber)
        if (result.isSuccess) {
            breakingNews = result.getOrElse { emptyList() }
        }
    }

    fun setCurrentArticle(article: Article) {
        currentArticle = article
    }

}