package com.example.androiddevelopment.di

import com.example.androiddevelopment.data.network.ApiService
import com.example.androiddevelopment.data.network.RetrofitInstance
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single {
        RetrofitInstance.retrofit
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}