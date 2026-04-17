package com.henry.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.henry.core.database.NewsDatabase
import com.henry.core.database.entity.ArticleEntity
import com.henry.core.database.entity.RemoteKeyEntity
import com.henry.data.mapper.toEntity
import com.henry.data.remote.api.NewsApiService

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsApiService: NewsApiService,
    private val database: NewsDatabase,
    private val country: String
) : RemoteMediator<Int, ArticleEntity>() {

    private val articleDao = database.articleDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val count = articleDao.countArticles()
        return if(count > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = false)
                val remoteKey = remoteKeyDao.getRemoteKeyByArticleId(lastItem.id)
                remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        return try {
            val response = newsApiService.getTopHeadlines(
                country = country,
                page = page,
                pageSize = state.config.pageSize
            )

            val articles = response.articles.map { it.toEntity() }

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    articleDao.clearArticles()
                    remoteKeyDao.clearAll()
                }
                val ids = articleDao.insertAll(articles)

                val keys = ids.map { id ->
                    RemoteKeyEntity(
                        articleId = id.toInt(),
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (articles.isEmpty()) null else page + 1
                    )
                }
                remoteKeyDao.insertAll(keys)
            }
            MediatorResult.Success(endOfPaginationReached = articles.isEmpty())
        } catch (e: Exception) {
            if (loadType == LoadType.REFRESH && articleDao.countArticles() > 0) {
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Error(e)
            }
        }
    }
}
