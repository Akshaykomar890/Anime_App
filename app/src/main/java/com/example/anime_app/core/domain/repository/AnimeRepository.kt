package com.example.anime_app.core.domain.repository

import com.example.anime_app.core.domain.model.Data
import com.example.anime_app.core.utils.SetResult
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    suspend fun getAnimeList():Flow<SetResult<List<Data>>>

    suspend fun getAnimeById(id:Int):Flow<SetResult<Data>>

}