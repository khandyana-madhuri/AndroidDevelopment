package com.example.androiddevelopment.presentation

import androidx.lifecycle.ViewModel
import com.example.androiddevelopment.data.model.User
import com.example.androiddevelopment.data.repository.Repository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber


class MainViewModel: ViewModel(), KoinComponent {
    private val userRepo: Repository by inject()

    suspend fun fetchUser() : List<User> {
        Timber.d("Users data ${userRepo.getUser()}")
       return userRepo.getUser()
    }
}