package com.henry.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henry.core.database.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles ORDER BY id ASC")
    fun getAll(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articles")
    suspend fun clearArticles()

    @Query("SELECT COUNT(*) FROM articles")
    suspend fun countArticles(): Int
}