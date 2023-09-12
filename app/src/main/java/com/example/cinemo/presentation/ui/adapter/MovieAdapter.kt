package com.example.cinemo.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinemo.R
import com.example.cinemo.databinding.MovieItemBinding
import com.example.cinemo.domain.model.MovieItemResponse
import com.example.cinemo.utils.Constants.DateTimeConfig.DATE_PATTERN_FULL_MONTH
import com.example.cinemo.utils.Constants.DateTimeConfig.DATE_TIME_PATTERN
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter(private val item: ArrayList<MovieItemResponse>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val musicItem = item[position]
        holder.bind(musicItem, onClickListener, position)
    }

    override fun getItemCount(): Int = item.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(musicItem: MovieItemResponse, onClickListener: OnClickListener?, position: Int) {
            binding.apply {
                genreTextView.text = musicItem.genre
                movieTitleTextView.text = musicItem.titleEn
                val date = convertDateTime(
                    musicItem.releaseDate,
                    DATE_TIME_PATTERN,
                    DATE_PATTERN_FULL_MONTH
                )
                movieReleaseDateTextView.text = date

                Glide.with(itemView.context)
                    .load(musicItem.posterUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(movieImageView)

                itemView.setOnClickListener {
                    onClickListener?.onClick(
                        position = position,
                        item = musicItem
                    )
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, item: MovieItemResponse)
    }

    fun convertDateTime(date: String, inputFormat: String, outputFormat: String): String {
        return try {
            val dateFormat =
                SimpleDateFormat(inputFormat, Locale.getDefault()).parse(date) ?: Date()
            SimpleDateFormat(outputFormat, Locale.getDefault()).format(dateFormat)
        } catch (exception: Exception) {
            exception.printStackTrace()
            "-"
        }
    }
}