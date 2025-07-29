package com.example.newsreaderapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).favoriteArticleDao()
    private val repo = FavoriteRepository(dao)

    // Expose LiveData to the UI
    val allFavorites: LiveData<List<FavouriteArticle>> = repo.allFavorites

    fun toggleFavorite(article: FavouriteArticle) = viewModelScope.launch {
        if (repo.isFavorite(article.url)) {
            repo.delete(article)
        } else {
            repo.insert(article)
        }
    }

    // For checking favorite status in background
    suspend fun isFavorite(url: String): Boolean = repo.isFavorite(url)
}
