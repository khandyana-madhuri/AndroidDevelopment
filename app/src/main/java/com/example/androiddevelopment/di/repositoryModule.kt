package com.example.androiddevelopment.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androiddevelopment.data.repository.UserRepositoryImpl
import com.example.androiddevelopment.data.room.database.UserDatabase
import com.example.androiddevelopment.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single(named("user_database")) {
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    single { get<UserDatabase>(named("user_database")).userDao() }

    single<UserRepository> { UserRepositoryImpl(get()) }
}