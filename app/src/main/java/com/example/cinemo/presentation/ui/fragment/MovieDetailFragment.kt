package com.example.cinemo.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.cinemo.R
import com.example.cinemo.databinding.FragmentMovieDetailBinding
import com.example.cinemo.domain.model.MovieItemResponse
import com.example.cinemo.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieViewModel by activityViewModels()
    private var movieDetail: MovieItemResponse? = null
    private var itemPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieDetail = MovieDetailFragmentArgs.fromBundle(it).movieDetail
            itemPosition = MovieDetailFragmentArgs.fromBundle(it).position
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetail?.let { movieDetail ->
            binding.apply {
                genreTextView.text = movieDetail.genre
                movieTitleTextView.text = movieDetail.titleEn
                movieDetailTextView.text = movieDetail.synopsisEn

                Glide.with(this@MovieDetailFragment)
                    .load(movieDetail.posterUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(movieImageView)
            }
        }

        binding.apply {
            val enableButton = movieDetail?.isFavorite ?: false
            addToFavoriteButton.isEnabled = !enableButton
            addToFavoriteButton.setOnClickListener {
                viewModel.addFavoriteMovieItem(itemPosition)
                Toast.makeText(
                    context,
                    "Add ${movieDetail?.titleEn} to Favorite",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}