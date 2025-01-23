package com.example.anime_app.core.di

import com.example.anime_app.core.data.remote.web.AnimeApi
import com.example.anime_app.core.data.repository.AnimeRepositoryImp
import com.example.anime_app.core.domain.repository.AnimeRepository
import com.example.anime_app.core.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit():AnimeApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimeApi::class.java)
    }


    @Provides
    @Singleton
    fun provideAnimeRepository(animeApi:AnimeApi): AnimeRepository {
        return AnimeRepositoryImp(animeApi)
    }


}