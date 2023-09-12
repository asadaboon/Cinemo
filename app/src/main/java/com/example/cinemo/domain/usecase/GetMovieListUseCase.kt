package com.example.cinemo.domain.usecase

import com.example.cinemo.data.service.MovieService
import com.example.cinemo.domain.model.MovieResponse
import com.example.cinemo.utils.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val repository: MovieService
) {
    operator fun invoke(): Flow<Results<MovieResponse>> = flow {
        try {
            emit(Results.Success(repository.getMovieList()))
        } catch (e: HttpException) {
            emit(Results.Error("$e"))
        } catch (e: IOException) {
            emit(Results.Error("$e"))
        }
    }
}