package com.example.cinemo.data.service

import com.example.cinemo.domain.model.MovieResponse
import retrofit2.http.GET

interface MovieService {

    @GET("get_movie_avaiable")
    suspend fun getMovieList(): MovieResponse
}