package com.henry.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henry.core.database.entity.ArticleEntity
import com.henry.core.database.entity.RemoteKeyEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>): List<Long>

    @Query("SELECT * FROM articles ORDER BY id ASC")
    fun getAll(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articles")
    suspend fun clearArticles()

    @Query("SELECT COUNT(*) FROM articles")
    suspend fun countArticles(): Int
}

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE articleId = :articleId")
    suspend fun getRemoteKeyByArticleId(articleId: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}