package com.example.anime_app.core.data.remote.web

import com.example.anime_app.core.data.remote.response.AnimeDto
import com.example.anime_app.core.data.remote.response.DataDto
import com.example.anime_app.core.data.remote.response.GetData
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    @GET("top/anime")
    suspend fun getAnimeData():AnimeDto

    @GET("anime/{mal_id}")
    suspend fun getAnimeById(
       @Path("mal_id") id:Int
    ):GetData


}