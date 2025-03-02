package com.example.ponti

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideApiHelper(): ApiHelper {
        return ApiHelper()
    }

    @Provides
    @Singleton
    fun providePontiRepository(apiService: ApiService, apiHelper: ApiHelper): PontiRepository {
        return PontiRepositoryImpl(apiService, apiHelper)
    }
}