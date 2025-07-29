package com.example.newsreaderapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchNews() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getNews()
                _articles.value = response.articles
            } catch (e: Exception) {
                Log.e("NewsFetchError", "Failed to load news", e)
            }
            _isLoading.value = false
        }
    }
}
