package com.example.androiddevelopment.data.repository

import com.example.androiddevelopment.data.remote.api.NewsApi
import com.example.androiddevelopment.data.remote.dto.ArticleDto
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun fetchNews(): List<ArticleDto> {
        return newsApi.getTopHeadLines(
            page = 1,
            apiKey = "a6e388ee189e4feeb165456ead840d32"
        ).articles
    }
}
