package com.example.newsreaderapp

import androidx.lifecycle.LiveData

class FavoriteRepository(private val dao: FavoriteArticleDao) {

    // LiveData to observe in UI
    val allFavorites: LiveData<List<FavouriteArticle>> = dao.getAllFavorites()

    suspend fun insert(article: FavouriteArticle) = dao.insertFavorite(article)
    suspend fun delete(article: FavouriteArticle) = dao.deleteFavorite(article)
    suspend fun isFavorite(url: String): Boolean = dao.isFavorite(url)
}
