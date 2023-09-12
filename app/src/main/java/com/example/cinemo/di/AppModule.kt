package com.example.cinemo.di

import com.example.cinemo.data.repository.MovieRepository
import com.example.cinemo.data.repository.MovieRepositoryImpl
import com.example.cinemo.utils.Constants.BASE_URL
import com.example.cinemo.data.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): MovieService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(service: MovieService): MovieRepository {
        return MovieRepositoryImpl(service)
    }
}