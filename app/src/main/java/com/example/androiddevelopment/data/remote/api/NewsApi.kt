package com.example.androiddevelopment.data.remote.api

import com.example.androiddevelopment.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("country") country: String = "us",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String
    ) : NewsResponseDto
}
