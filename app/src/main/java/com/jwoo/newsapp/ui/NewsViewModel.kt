package com.jwoo.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jwoo.newsapp.models.NewsResponse
import com.jwoo.newsapp.respository.NewsRepository
import com.jwoo.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage: Int = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsPage: Int = 1

    init {
        getBreakingNews("ph")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }
}