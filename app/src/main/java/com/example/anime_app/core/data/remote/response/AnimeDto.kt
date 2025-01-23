package com.example.anime_app.core.data.remote.response

data class AnimeDto(
    val `data`: List<DataDto>?,
    val pagination: PaginationDto?
)

data class GetData(
    val data:DataDto
)

