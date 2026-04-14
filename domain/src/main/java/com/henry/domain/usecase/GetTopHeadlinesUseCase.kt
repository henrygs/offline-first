package com.henry.domain.usecase

import com.henry.domain.model.Article
import com.henry.domain.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(country: String): List<Article> {
        return newsRepository.getTopHeadlines(country)
    }
}
