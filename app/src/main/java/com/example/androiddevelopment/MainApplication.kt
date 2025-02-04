package com.example.androiddevelopment

import android.app.Application
import com.example.androiddevelopment.di.networkModule
import com.example.androiddevelopment.di.repositoryModule
import com.example.androiddevelopment.di.viewModelModule
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}