package com.example.anime_app.core.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anime_app.core.domain.repository.AnimeRepository
import com.example.anime_app.core.utils.ErrorType
import com.example.anime_app.core.utils.SetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeRepositoryImp:AnimeRepository
):ViewModel() {

    private val _animeListState = MutableStateFlow(AnimeState())
    var animeListState = _animeListState.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading  = _isLoading.asStateFlow()


    init {
        getAnimeData()
    }

     private fun getAnimeData() {
        viewModelScope.launch {
            _isLoading.value = true
            animeRepositoryImp.getAnimeList().collectLatest {
                data->
                when(data){
                    is SetResult.OnSuccess -> {
                        data.data?.let {
                            getData->
                            _animeListState.update {
                                it.copy(animeList = getData )
                            }
                        }
                        _isLoading.value = false
                    }
                    is SetResult.OnError -> {
                        data.errorType?.let {
                                errorData->
                            val errorMessage =  when(errorData){
                                ErrorType.NETWORK_ERROR -> "Please check your internet connection and try again."
                                ErrorType.AUTHENTICATION_ERROR -> "Authentication failed. Please log in again."
                                ErrorType.UNKNOWN_ERROR -> "An unknown error occurred. Please try again."
                            }
                            _animeListState.update {
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