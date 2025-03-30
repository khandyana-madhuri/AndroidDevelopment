package com.example.androiddevelopment.data.remote

import com.example.androiddevelopment.domain.model.ApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("objects")
    suspend fun getObjects(): List<ApiResponse>
}