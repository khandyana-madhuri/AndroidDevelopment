package com.example.androiddevelopment.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevelopment.data.repository.NewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(val newsRepositoryImpl: NewsRepositoryImpl): ViewModel() {
    init {
        viewModelScope.launch {
            try {
                val news = newsRepositoryImpl.fetchNews()
                Log.d("NEWS_API", "Articles: ${news.size}")
            } catch(e: Exception) {
                Log.d("NEWS_API", "error", e)
            }
        }
    }
}