package com.henry.offline_first.di

import com.henry.data.remote.api.NewsApiService
import com.henry.data.repository.NewsRepositoryImpl
import com.henry.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository

    companion object {
        @Provides
        @Singleton
        fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
            return retrofit.create(NewsApiService::class.java)
        }
    }
}
