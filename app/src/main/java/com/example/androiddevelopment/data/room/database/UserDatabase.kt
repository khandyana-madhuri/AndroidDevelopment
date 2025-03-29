package com.example.androiddevelopment.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androiddevelopment.data.room.dao.UserDao
import com.example.androiddevelopment.data.room.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}