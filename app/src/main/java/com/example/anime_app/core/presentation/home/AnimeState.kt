package com.example.anime_app.core.presentation.home

import com.example.anime_app.core.domain.model.Data

data class AnimeState(
    val animeList:List<Data> = emptyList(),
    val errorMessage:String? = null
)