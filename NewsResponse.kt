package com.example.newsreaderapp

data class NewsResponse(
    val totalArticles: Int,
    val articles: List<Article>
)
