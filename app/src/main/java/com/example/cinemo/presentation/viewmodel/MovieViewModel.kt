package com.example.cinemo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemo.domain.model.MovieItemResponse
import com.example.cinemo.domain.model.MovieResponse
import com.example.cinemo.domain.usecase.GetMovieListUseCase
import com.example.cinemo.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    val movieListLiveData = MutableLiveData<Results<MovieResponse>>()
    val movieFavoriteListLiveData = MutableLiveData<ArrayList<MovieItemResponse>>()
    val updateMovieLiveData = MutableLiveData<Int>()
    private val movieList: ArrayList<MovieItemResponse> = ArrayList()
    val searchMovieFavoriteListLiveData = MutableLiveData<Pair<MovieResponse?, Boolean>>()
    private val movieSearchItemList: ArrayList<MovieItemResponse> = ArrayList()
    private var movieSearch: MovieResponse? = null

    init {
        getMovieList()
    }

    private fun getMovieList() {
        getMovieListUseCase().onStart {
            Log.e("BOONTHAM", "searchMusic: onStart")
        }.onEach { result ->
            when (result) {
                is Results.Loading -> {
                    movieListLiveData.value = result
                }

                is Results.Success -> {
                    movieListLiveData.postValue(result)
                }

                is Results.Error -> {
                    movieListLiveData.value = result
                }
            }
        }.onCompletion {
            Log.e("BOONTHAM", "getList: onCompletion")
        }.launchIn(viewModelScope)
    }

    fun addFavoriteMovieItem(itemPosition: Int) {
        movieListLiveData.value?.data?.movies?.get(itemPosition)?.apply {
            isFavorite = true
        }

        updateMovieLiveData.value = itemPosition
    }

    fun getFavoriteMovieItem() {
        val favoriteList = movieListLiveData.value?.data?.movies?.filter {
            it.isFavorite
        }

        movieList.clear()
        favoriteList?.let { movieFavoriteList ->
            movieList.addAll(movieFavoriteList)
        }

        movieFavoriteListLiveData.value = movieList
    }

    fun searchMovie(keyWord: String) {
        val searchMovieList =
            movieListLiveData.value?.data?.movies?.find { it.titleEn.lowercase(Locale.getDefault()) == keyWord }

        movieSearchItemList.clear()
        searchMovieList?.let { movieResponse ->
            movieSearchItemList.add(movieResponse)
            movieSearch = MovieResponse(movieSearchItemList)
        } ?: run {
            movieSearch = null
            searchMovieFavoriteListLiveData.value = Pair(first = null, second = false)
        }

        movieSearch?.let { movieResponse ->
            searchMovieFavoriteListLiveData.value = Pair(first = movieResponse, second = true)
        } ?: run {
            movieListLiveData.value = movieListLiveData.value
        }
    }
}