package com.example.newsreaderapp

data class Article(
    val title: String,
    val description: String?,
    val url: String,
    val content: String?,
    val image: String?,
    val publishedAt: String,
    var isFavorite: Boolean = false
)
