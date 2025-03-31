package com.example.androiddevelopment.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androiddevelopment.data.model.ProductEntity
import com.example.androiddevelopment.data.room.dao.ProductDao
import com.example.androiddevelopment.data.room.dao.UserDao
import com.example.androiddevelopment.data.room.entities.UserEntity

@Database(
    entities = [ProductEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
}