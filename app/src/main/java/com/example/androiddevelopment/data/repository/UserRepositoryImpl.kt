package com.example.androiddevelopment.data.repository

import com.example.androiddevelopment.data.room.dao.UserDao
import com.example.androiddevelopment.data.room.entities.UserEntity
import com.example.androiddevelopment.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun insertUser(user: UserEntity) {
        userDao.insert(user)
    }

    override suspend fun getUsers(): UserEntity {
        return userDao.getUsers()
    }
}