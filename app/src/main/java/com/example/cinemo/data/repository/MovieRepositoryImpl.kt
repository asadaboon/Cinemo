package com.example.cinemo.data.repository

import com.example.cinemo.data.service.MovieService
import com.example.cinemo.domain.model.MovieResponse

class MovieRepositoryImpl(private val service: MovieService) : MovieRepository {

    override suspend fun getMovieList(): MovieResponse {
        return service.getMovieList()
    }
}