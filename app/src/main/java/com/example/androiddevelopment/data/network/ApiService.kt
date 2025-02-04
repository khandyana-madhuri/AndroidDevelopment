package com.example.androiddevelopment.data.network

import com.example.androiddevelopment.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users/1")
    suspend fun getUser() : User
}