package com.example.androiddevelopment.di

import com.example.androiddevelopment.presentation.viewmodel.WeatherViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { WeatherViewModel(get()) }
}