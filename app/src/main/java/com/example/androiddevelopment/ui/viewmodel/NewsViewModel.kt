package com.example.androiddevelopment.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevelopment.data.remote.dto.ArticleDto
import com.example.androiddevelopment.data.repository.NewsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class NewsUiState {
    object Loading: NewsUiState()
    data class Success(val articles: List<ArticleDto>) : NewsUiState()
    data class Error(val message: String): NewsUiState()
}

@HiltViewModel
class NewsViewModel @Inject constructor(val newsRepositoryImpl: NewsRepositoryImpl): ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            try {
                val articles = newsRepositoryImpl.fetchNews()
                _uiState.value = NewsUiState.Success(articles)
            } catch(e: Exception) {
                _uiState.value = NewsUiState.Error(e.localizedMessage ?: "Something Went Wrong")
            }
        }
    }
}