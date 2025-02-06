package com.example.androiddevelopment.data.network

import com.example.androiddevelopment.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUser() : List<User>
}