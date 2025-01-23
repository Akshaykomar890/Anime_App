package com.example.anime_app.core.data.Mapper

import com.example.anime_app.core.data.remote.response.AiredDto
import com.example.anime_app.core.data.remote.response.AnimeDto
import com.example.anime_app.core.data.remote.response.BroadcastDto
import com.example.anime_app.core.data.remote.response.DataDto
import com.example.anime_app.core.data.remote.response.DemographicDto
import com.example.anime_app.core.data.remote.response.FromDto
import com.example.anime_app.core.data.remote.response.GenreDto
import com.example.anime_app.core.data.remote.response.ImagesDto
import com.example.anime_app.core.data.remote.response.ImagesXDto
import com.example.anime_app.core.data.remote.response.ItemsDto
import com.example.anime_app.core.data.remote.response.JpgDto
import com.example.anime_app.core.data.remote.response.LicensorDto
import com.example.anime_app.core.data.remote.response.PaginationDto
import com.example.anime_app.core.data.remote.response.ProducerDto
import com.example.anime_app.core.data.remote.response.PropDto
import com.example.anime_app.core.data.remote.response.StudioDto
import com.example.anime_app.core.data.remote.response.ThemeDto
import com.example.anime_app.core.data.remote.response.TitleDto
import com.example.anime_app.core.data.remote.response.ToDto
import com.example.anime_app.core.data.remote.response.TrailerDto
import com.example.anime_app.core.data.remote.response.WebpDto
import com.example.anime_app.core.domain.model.Aired
import com.example.anime_app.core.domain.model.Anime
import com.example.anime_app.core.domain.model.Broadcast
import com.example.anime_app.core.domain.model.Data
import com.example.anime_app.core.domain.model.Demographic
import com.example.anime_app.core.domain.model.Prop
import com.example.anime_app.core.domain.model.From
import com.example.anime_app.core.domain.model.Genre
import com.example.anime_app.core.domain.model.Images
import com.example.anime_app.core.domain.model.ImagesX
import com.example.anime_app.core.domain.model.Items
import com.example.anime_app.core.domain.model.Jpg
import com.example.anime_app.core.domain.model.Licensor
import com.example.anime_app.core.domain.model.Pagination
import com.example.anime_app.core.domain.model.Producer
import com.example.anime_app.core.domain.model.Studio
import com.example.anime_app.core.domain.model.Theme
import com.example.anime_app.core.domain.model.Title
import com.example.anime_app.core.domain.model.To
import com.example.anime_app.core.domain.model.Trailer
import com.example.anime_app.core.domain.model.Webp

fun AnimeDto.toAnime():Anime{
    return Anime(
        data = data?.map { it.toData() }?: emptyList() ,
        pagination = pagination?.toPagination()?:Pagination(0,false,Items(
            0,0,0,
        ),
            0
        )
    )
}

fun DataDto.toData():Data{
    return Data(
        aired = aired?.toAired() ?: Aired("", Prop(From(0, 0, 0), To(0, 0, 0)), "", ""),
        airing = airing ?: false,
        approved = approved ?: false,
        background = background ?: "",
        broadcast = broadcast?.toBroadcast()?:Broadcast("","","",""),
        demographics = demographics?.map { it.toDemographic() }?: emptyList(),
        duration = duration ?: "",
        episodes = episodes ?: 0,
        explicit_genres = explicit_genres ?: emptyList(),
        favorites = favorites ?: 0,
        genres = genres ?.map { it.toGenre() }?: emptyList(),
        images = images?.toImage()?: Images(
            jpg = Jpg("", "", ""),
            webp = Webp("", "", "")
        ),
        licensors = licensors ?.map { it.toLicensor() }?: emptyList(),
        mal_id = mal_id ?: 0,
        members = members ?: 0,
        popularity = popularity ?: 0,
        producers = producers ?.map { it.toProducer() }?: emptyList(),
        rank = rank ?: 0,
        rating = rating ?: "",
        score = score ?: 0.0,
        scored_by = scored_by ?: 0,
        season = season ?: "",
        source = source ?: "",
        status = status ?: "",
        studios = studios ?.map { it.toStudio() }?: emptyList(),
        synopsis = synopsis ?: "",
        themes = themes ?.map { it.toTheme() }?: emptyList(),
        title = title ?: "",
        title_english = title_english ?: "",
        title_japanese = title_japanese ?: "",
        title_synonyms = title_synonyms ?: emptyList(),
        titles = titles ?.map { it.toTitle() }?: emptyList(),
        trailer = trailer?.toTrailer() ?: Trailer("", ImagesX("", "", "", "", ""), "", ""),
        type = type ?: "",
        url = url ?: "",
        year = year ?: 0

    )
}


fun PaginationDto.toPagination(): Pagination {
    return Pagination(
        current_page = current_page?:0,
        has_next_page = has_next_page?:false,
        items = items?.toItems()?:Items(0,0,0) ,
        last_visible_page = last_visible_page?:0
    )
}

fun ItemsDto.toItems():Items{
    return Items(
        count = count?:0,
        per_page = per_page?:0,
        total = total?:0
    )
}

fun AiredDto.toAired(): Aired {
    return Aired(
        from = from ?: "",
        prop = prop?.toProp()?:Prop(
            From(
                0,0,0
            ),
            to = To(
                0,0,0
            )
        ),
        string = string ?: "",
        to = to ?: ""
    )
}

fun PropDto.toProp():Prop{
    return Prop(
        from = from?.toForm()?:From(0,0,0),
        to = to?.toTo()?:To(0,0,0),
    )
}

fun FromDto.toForm():From {
    return From(
        day = day?:0,
        month = month?:0,
        year = year?:0
    )
}

fun ToDto.toTo():To {
    return To(
        day = day?:0,
        month = month?:0,
        year = year?:0
    )
}

fun BroadcastDto.toBroadcast():Broadcast{
    return Broadcast(
        day = day?:"",
        string = string?:"",
        time = time?:"",
        timezone = timezone?:""
    )
}

fun DemographicDto.toDemographic():Demographic{
    return Demographic(
        mal_id = mal_id?:0,
        name = name?:"",
        type = type?:"",
        url = url?:""
    )
}

fun GenreDto.toGenre():Genre{
    return Genre(
        mal_id = mal_id?:0,
        name = name?:"",
        type = type?:"",
        url = url?:""
    )
}


fun ImagesDto.toImage():Images{
    return Images(
        jpg = jpg?.toJpg()?:Jpg(",","",""),
        webp = webp?.toWebp()?:Webp(",","",""),
    )
}

fun JpgDto.toJpg():Jpg{
    return Jpg(
        image_url = image_url?:"",
        large_image_url = large_image_url?:"",
        small_image_url = small_image_url?:""
    )
}

fun WebpDto.toWebp():Webp{
    return Webp(
        image_url = image_url?:"",
        large_image_url = large_image_url?:"",
        small_image_url = small_image_url?:""
    )
}


fun LicensorDto.toLicensor(): Licensor {
    return Licensor(
        mal_id = mal_id?:0,
        name = name?:"",
        type = type?:"",
        url = url?:""
    )
}

fun ProducerDto.toProducer(): Producer {
    return Producer(
        mal_id = mal_id?:0,
        name = name?:"",
        type = type?:"",
        url = url?:""
    )
}


fun StudioDto.toStudio(): Studio {
    return Studio(
        mal_id = mal_id?:0,
        name = name?:"",
        type = type?:"",
        url = url?:""
    )
}

fun ThemeDto.toTheme(): Theme {
    return Theme(
        mal_id = mal_id?:0,
        name = name?:"",
        type = type?:"",
        url = url?:""
    )
}


fun TrailerDto.toTrailer():Trailer{
    return Trailer(
        embed_url = embed_url?:"",
        images = images?.toIamgesX() ?: ImagesX("", "", "", "", ""),
        url = url?:"",
        youtube_id = youtube_id?:""
    )
}

fun ImagesXDto.toIamgesX():ImagesX{
    return ImagesX(
        large_image_url = large_image_url?:"",
        small_image_url = small_image_url?:"",
        maximum_image_url = maximum_image_url?:"",
        medium_image_url = medium_image_url?:"",
        image_url = image_url?:""
    )
}

fun TitleDto.toTitle():Title{
    return Title(
        title = title?:"",
        type = type?:""
    )
}