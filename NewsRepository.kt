package com.example.newsreaderapp

class NewsRepository {
    suspend fun getNews(): NewsResponse {
        return RetrofitInstance.api.getTopHeadlines(apiKey = "c61f3d2b3d5cf31b1c213a8f6b1d1917")
    }
}
