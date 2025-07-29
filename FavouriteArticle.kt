package com.example.newsreaderapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_articles")
data class FavouriteArticle(
    @PrimaryKey val url: String, // Use unique identifier
    val title: String?,
    val description: String?,
    val imageUrl: String?
)