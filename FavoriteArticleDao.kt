package com.example.newsreaderapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(article: FavouriteArticle)

    @Delete
    suspend fun deleteFavorite(article: FavouriteArticle)

    @Query("SELECT * FROM favorite_articles")
    fun getAllFavorites(): LiveData<List<FavouriteArticle>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_articles WHERE url = :url)")
    suspend fun isFavorite(url: String): Boolean
}
