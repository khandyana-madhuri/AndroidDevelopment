package com.example.androiddevelopment.domain.repository

import com.example.androiddevelopment.data.room.dao.UserDao
import com.example.androiddevelopment.data.room.entities.UserEntity

interface UserRepository {
    suspend fun insertUser(user: UserEntity)
    suspend fun getUsers() : UserEntity

}