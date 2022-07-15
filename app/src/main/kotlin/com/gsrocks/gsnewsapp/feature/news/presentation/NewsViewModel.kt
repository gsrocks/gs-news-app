package com.gsrocks.gsnewsapp.feature.news.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.gsnewsapp.core.presentation.Resource
import com.gsrocks.gsnewsapp.core.utils.empty
import com.gsrocks.gsnewsapp.feature.news.domain.model.Article
import com.gsrocks.gsnewsapp.feature.news.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var searchedNews by mutableStateOf<Resource<List<Article>>>(Resource.Success(emptyList()))
        private set

    var currentArticle: Article? = null
        private set

    private val _searchQuery = MutableStateFlow(String.empty)
    val searchQuery = _searchQuery.asStateFlow()

    val breakingNewsFlow = newsRepository.getBreakingNewsFlow("us")

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .collectLatest {
                    if (it.isNotBlank()) {
                        searchNews(it, 1)
                    } else {
                        searchedNews = Resource.Success(emptyList())
                    }
                }
        }
    }

    private fun searchNews(query: String, pageNumber: Int) = viewModelScope.launch {
        searchedNews = Resource.Loading()
        val result = newsRepository.searchNews(query, pageNumber)
        searchedNews = if (result.isSuccess) {
            Resource.Success(
                result.getOrElse { emptyList() }
            )
        } else {
            Resource.Failure(result.exceptionOrNull())
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun setCurrentArticle(article: Article) {
        currentArticle = article
    }

    fun saveFavouriteArticle(article: Article) = viewModelScope.launch {
        newsRepository.insertOrUpdateFavouriteArticle(article)
    }

    fun deleteFavouriteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteFavouriteArticle(article)
    }

    fun getFavouriteArticles() = newsRepository.getFavouriteArticles()
}