package com.example.androiddevelopment.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageViewerViewModel : ViewModel() {

    val openCamera = MutableLiveData<Boolean>()
    val openGallery = MutableLiveData<Boolean>()

    fun onClickOpenCamera() {
        openCamera.postValue(true)
    }

    fun onClickOpenGallery() {
        openGallery.postValue(true)
    }
}