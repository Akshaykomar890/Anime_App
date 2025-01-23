package com.example.anime_app.core.data.remote.response

data class PaginationDto(
    val current_page: Int?,
    val has_next_page: Boolean?,
    val items: ItemsDto?,
    val last_visible_page: Int?
)