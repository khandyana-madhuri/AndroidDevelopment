package com.example.androiddevelopment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevelopment.data.room.entities.UserEntity
import com.example.androiddevelopment.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<UserEntity?>()
    val user: LiveData<UserEntity?> get() = _users
    val signIn = MutableLiveData<Boolean>()

    fun handleGoogleSignInResult(user: FirebaseUser) {
        viewModelScope.launch {
            val userEntity = UserEntity(
                userId = user.uid,
                displayName = user.displayName,
                email = user.email,
                photoUrl = user.photoUrl?.toString()
            )
            userRepository.insertUser(userEntity)
        }
    }

    fun onClickSignIn() {
        signIn.postValue(true)
    }
    
}