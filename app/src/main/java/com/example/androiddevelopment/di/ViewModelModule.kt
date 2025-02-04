package com.example.androiddevelopment.di

import com.example.androiddevelopment.presentation.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel() }
}