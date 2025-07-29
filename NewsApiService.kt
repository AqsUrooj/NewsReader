package com.example.newsreaderapp

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("token") apiKey: String,
        @Query("lang") language: String = "en"
    ): NewsResponse
}

