package com.henry.data.remote.api

import com.henry.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String): NewsResponseDto
}
