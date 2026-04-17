package com.henry.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.henry.core.database.NewsDatabase
import com.henry.core.database.entity.ArticleEntity
import com.henry.data.mapper.toEntity
import com.henry.data.remote.api.NewsApiService

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsApiService: NewsApiService,
    private val database: NewsDatabase,
    private val country: String
) : RemoteMediator<Int, ArticleEntity>() {

    private val articleDao = database.articleDao()

    override suspend fun initialize(): InitializeAction {
        return if (articleDao.countArticles() > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = calculatePage(loadType, state.config.pageSize)
            ?: return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            fetchAndSave(page, state.config.pageSize, loadType)
        } catch (e: Exception) {
            handleError(e, loadType)
        }
    }

    private suspend fun calculatePage(loadType: LoadType, pageSize: Int): Int? {
        return when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> null
            LoadType.APPEND -> {
                val count = articleDao.countArticles()
                if (count == 0) null else (count / pageSize) + 1
            }
        }
    }

    private suspend fun fetchAndSave(page: Int, pageSize: Int, loadType: LoadType): MediatorResult {
        val response = newsApiService.getTopHeadlines(
            country = country,
            page = page,
            pageSize = pageSize
        )

        val articles = response.articles.map { it.toEntity() }

        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                articleDao.clearArticles()
            }
            articleDao.insertAll(articles)
        }

        return MediatorResult.Success(endOfPaginationReached = articles.isEmpty())
    }

    private suspend fun handleError(e: Exception, loadType: LoadType): MediatorResult {
        return if (loadType == LoadType.REFRESH && articleDao.countArticles() > 0) {
            MediatorResult.Success(endOfPaginationReached = false)
        } else {
            MediatorResult.Error(e)
        }
    }
}
