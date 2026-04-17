package com.henry.data.mapper

import com.henry.core.database.entity.ArticleEntity
import com.henry.data.remote.dto.ArticleDto
import com.henry.domain.model.Article

fun ArticleDto.toEntity(): ArticleEntity {
    return ArticleEntity(
        title = title,
        description = description,
        author = author,
        url = url,
        publishedAt = publishedAt,
        urlToImage = urlToImage
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        title = title,
        description = description,
        author = author,
        url = url,
        publishedAt = publishedAt,
        urlToImage = urlToImage
    )
}
