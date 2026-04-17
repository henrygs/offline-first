package com.henry.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.henry.core.database.NewsDatabase
import com.henry.data.mapper.toDomain
import com.henry.data.mediator.NewsRemoteMediator
import com.henry.data.remote.api.NewsApiService
import com.henry.domain.model.Article
import com.henry.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val database: NewsDatabase
) : NewsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHeadlines(country: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 3,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = NewsRemoteMediator(newsApiService, database, country),
            pagingSourceFactory = { database.articleDao().getAll() }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomain() }
        }
    }
}
