package com.example.androiddevelopment.data.repository

import com.example.androiddevelopment.data.network.ApiService
import com.example.androiddevelopment.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class Repository(private val apiService: ApiService) {
    suspend fun getUser(): List<User> {
        return withContext(Dispatchers.IO) {
            apiService.getUser()
        }
    }
}