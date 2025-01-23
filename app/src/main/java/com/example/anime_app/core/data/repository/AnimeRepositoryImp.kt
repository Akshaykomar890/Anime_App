package com.example.anime_app.core.data.repository

import android.util.Log
import com.example.anime_app.core.data.Mapper.toAnime
import com.example.anime_app.core.data.Mapper.toData
import com.example.anime_app.core.data.remote.web.AnimeApi
import com.example.anime_app.core.domain.model.Data
import com.example.anime_app.core.domain.repository.AnimeRepository
import com.example.anime_app.core.utils.ErrorType
import com.example.anime_app.core.utils.SetResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class AnimeRepositoryImp @Inject constructor(
    private val animeApi: AnimeApi
): AnimeRepository {
    override suspend fun getAnimeList(): Flow<SetResult<List<Data>>> {
        return flow {

            try {
                val getAnimeData = animeApi.getAnimeData()


                emit(
                    SetResult.OnSuccess(
                        getAnimeData.data?.map {
                            it.toData()
                        }?:emptyList()
                    )
                )


            }
            catch (e:Exception){
                emit(SetResult.OnError(
                    ErrorType.UNKNOWN_ERROR,
                ))
            }
            catch (e:IOException){
                emit(SetResult.OnError(
                    ErrorType.UNKNOWN_ERROR
                ))
            }

            return@flow
        }
    }

    override suspend fun getAnimeById(id: Int): Flow<SetResult<Data>> {
        return flow {
            try {
                val getAnimeById = animeApi.getAnimeById(id)
                Log.d("AnimeRepository", "API Response: $getAnimeById")
                emit(SetResult.OnSuccess(
                    getAnimeById.data.toData()
                ))
            } catch (e: Exception) {
                emit(SetResult.OnError(ErrorType.UNKNOWN_ERROR))
            }
        }
        }
    }


