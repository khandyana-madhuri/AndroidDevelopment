package com.example.androiddevelopment.data.remote.dto

data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)
