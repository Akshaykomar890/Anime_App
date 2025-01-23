package com.example.anime_app.core.presentation.detail

import com.example.anime_app.core.domain.model.Data

data class DetailsState(
    val animeId:Data? = null ,
    val errorMessage:String? = null,
    val videoUrl: String? = null,
    val posterUrl: String? = null,
)