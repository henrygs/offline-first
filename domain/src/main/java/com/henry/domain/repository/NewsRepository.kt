package com.henry.domain.repository

import com.henry.domain.model.Article

interface NewsRepository {
    fun getTopHeadlines(country: String): List<Article>
}
