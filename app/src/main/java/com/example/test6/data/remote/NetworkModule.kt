package com.example.test6.data.remote

import com.example.test6.presentation.tour.TourRepository
import com.example.test6.presentation.tour.TourRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideTourApi(retrofit: Retrofit): TourApi =
        retrofit.create(TourApi::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(): ApiHelper = ApiHelper()

    @Provides
    @Singleton
    fun provideTourRepository(
        tourApi: TourApi,
        apiHelper: ApiHelper
    ): TourRepository = TourRepositoryImpl(tourApi, apiHelper)
}