package com.example.androiddevelopment.di

import androidx.room.Room
import com.example.androiddevelopment.data.remote.ApiService
import com.example.androiddevelopment.data.repository.ProductRepositoryImpl
import com.example.androiddevelopment.data.repository.UserRepositoryImpl
import com.example.androiddevelopment.data.room.database.AppDatabase
import com.example.androiddevelopment.domain.repository.ProductRepository
import com.example.androiddevelopment.domain.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().userDao() }

    single<UserRepository> { UserRepositoryImpl(get()) }

    single {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder().addInterceptor(logging).build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.restful-api.dev/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ApiService::class.java) }

    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}