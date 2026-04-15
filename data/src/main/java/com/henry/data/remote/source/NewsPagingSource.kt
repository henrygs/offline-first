package com.henry.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.henry.data.remote.api.NewsApiService
import com.henry.domain.model.Article


class NewsPagingSource(
    private val newsApiService: NewsApiService,
    private val country: String
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = newsApiService.getTopHeadlines(
                country = country,
                page = page,
                pageSize = params.loadSize
            )

            val articles = response.articles.map { it.toDomain() }

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}