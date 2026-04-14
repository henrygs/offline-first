package com.henry.data.remote.dto

import com.henry.domain.model.Article

data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)

data class ArticleDto(
    val title: String?,
    val description: String?,
    val author: String?,
    val url: String?,
    val publishedAt: String?,
    val urlToImage: String?
) {
    fun toDomain(): Article {
        return Article(
            title = title,
            description = description,
            author = author,
            url = url,
            publishedAt = publishedAt,
            urlToImage = urlToImage
        )
    }
}
