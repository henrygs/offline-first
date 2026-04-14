package com.henry.domain.usecase

import com.henry.domain.model.Article
import com.henry.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(country: String): Flow<List<Article>> {
        return flow {
            val result = newsRepository.getTopHeadlines(country)
            emit(result)
        }
    }
}
