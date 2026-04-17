package com.henry.core.database.di

import android.content.Context
import androidx.room.Room
import com.henry.core.database.NewsDatabase
import com.henry.core.database.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_database"
        ).build()
    }

    @Provides
    fun provideArticleDao(database: NewsDatabase): ArticleDao = database.articleDao()
}