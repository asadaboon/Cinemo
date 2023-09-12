package com.example.cinemo.data.repository

import com.example.cinemo.domain.model.MovieResponse

interface MovieRepository {

    suspend fun getMovieList(): MovieResponse
}