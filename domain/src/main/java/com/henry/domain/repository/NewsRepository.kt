package com.henry.domain.repository

import androidx.paging.PagingData
import com.henry.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(country: String): Flow<PagingData<Article>>
}
