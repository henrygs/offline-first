package com.henry.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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