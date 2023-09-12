package com.example.cinemo.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("movies")
    val movies: ArrayList<MovieItemResponse>
) : Parcelable