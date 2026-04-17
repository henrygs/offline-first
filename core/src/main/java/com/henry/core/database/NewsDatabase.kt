package com.henry.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henry.core.database.dao.ArticleDao
import com.henry.core.database.dao.RemoteKeyDao
import com.henry.core.database.entity.ArticleEntity
import com.henry.core.database.entity.RemoteKeyEntity

@Database(
    entities = [ArticleEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)

abstract class NewsDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}