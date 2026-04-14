package com.henry.data.repository

import com.henry.data.remote.api.NewsApiService
import com.henry.domain.model.Article
import com.henry.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsRepository {

    override suspend fun getTopHeadlines(country: String): List<Article> {
        return newsApiService.getTopHeadlines(country).articles.map { it.toDomain() }
    }
}
