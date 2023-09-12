package com.example.cinemo.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieItemResponse(
    @SerializedName("actor")
    val actor: String,
    @SerializedName("advance_ticket")
    val advanceTicket: String,
    @SerializedName("date_update")
    val dateUpdate: String,
    @SerializedName("director")
    val director: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("movieCode")
    val movieCode: List<String>,
    @SerializedName("now_showing")
    val nowShowing: String,
    @SerializedName("poster_ori")
    val posterOri: String,
    @SerializedName("poster_url")
    val posterUrl: String,
    @SerializedName("priority")
    val priority: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("rating_id")
    val ratingId: Int,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("show_buyticket")
    val showBuyTicket: String,
    @SerializedName("sneak_date")
    val sneakDate: String,
    @SerializedName("synopsis_en")
    val synopsisEn: String,
    @SerializedName("synopsis_th")
    val synopsisTh: String,
    @SerializedName("title_en")
    val titleEn: String,
    @SerializedName("title_th")
    val titleTh: String,
    @SerializedName("tr_hd")
    val trHd: String,
    @SerializedName("tr_ios")
    val trIos: String,
    @SerializedName("tr_mp4")
    val trMp4: String,
    @SerializedName("tr_sd")
    val trSd: String,
    @SerializedName("trailer")
    val trailer: String,
    @SerializedName("trailer_cms_id")
    val trailerCmsId: String,
    @SerializedName("trailer_ivx_key")
    val trailerIvxKey: String,
    var isFavorite: Boolean = false
) : Parcelable