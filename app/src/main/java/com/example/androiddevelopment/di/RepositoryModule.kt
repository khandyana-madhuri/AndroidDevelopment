package com.example.androiddevelopment.di

import com.example.androiddevelopment.data.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        Repository(get())
    }
}