package com.henry.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val articleId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val description: String?,
    val author: String?,
    val url: String?,
    val publishedAt: String?,
    val urlToImage: String?
)