package com.example.anime_app.core.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anime_app.core.domain.repository.AnimeRepository
import com.example.anime_app.core.utils.ErrorType
import com.example.anime_app.core.utils.SetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
):ViewModel() {



    private val _animeIdState = MutableStateFlow(DetailsState())
    val animeIdState = _animeIdState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun getAnimeById(id:Int){
        viewModelScope.launch {
            _isLoading.value = true
                animeRepository.getAnimeById(id = id).collectLatest {
                    result->
                when(result){
                    is SetResult.OnSuccess -> {
                        result.data.let {
                           data->
                            val trailerUrl = data?.trailer?.youtube_id
                            val posterUrl = data?.images?.jpg?.large_image_url

                            _animeIdState.update {
                                it.copy(animeId = data,
                                    videoUrl = trailerUrl,
                                    posterUrl = posterUrl
                                )
                            }
                        }
                        _isLoading.value = false
                    }
                    is SetResult.OnError -> {

                        result.errorType?.let {
                                errorData->
                            val errorMessage =  when(errorData){
                                ErrorType.NETWORK_ERROR -> "Please check your internet connection and try again."
                                ErrorType.AUTHENTICATION_ERROR -> "Authentication failed. Please log in again."
                                ErrorType.UNKNOWN_ERROR -> "An unknown error occurred. Please try again."
                            }
                            _animeIdState.update {
                                it.copy(errorMessage = errorMessage)
                            }
                            _isLoading.value = false
                        }
                    }
                }
            }

        }
    }






}