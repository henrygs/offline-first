package com.henry.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henry.core.database.dao.ArticleDao
import com.henry.core.database.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}