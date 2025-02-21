package com.example.androiddevelopment.di

import com.example.androiddevelopment.data.Repository.WeatherRepo
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherRepo(get()) }
}