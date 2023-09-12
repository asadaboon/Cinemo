package com.example.cinemo.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemo.databinding.FragmentMyFavoriteBinding
import com.example.cinemo.domain.model.MovieItemResponse
import com.example.cinemo.presentation.ui.adapter.MovieAdapter
import com.example.cinemo.presentation.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFavoriteFragment : Fragment() {

    private lateinit var binding: FragmentMyFavoriteBinding
    private val viewModel: MovieViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteMovieItem()
        setUpObserve()
    }

    private fun setUpObserve() {
        viewModel.movieFavoriteListLiveData.observe(viewLifecycleOwner) { result ->
            binding.apply {
                if (result.isNotEmpty()) {
                    emptyViewLayout.isVisible = false
                    contentViewLayout.isVisible = true

                    movieAdapter = MovieAdapter(result)
                    movieFavoriteRecyclerView.setHasFixedSize(true)
                    movieFavoriteRecyclerView.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    movieFavoriteRecyclerView.adapter = movieAdapter
                } else {
                    emptyViewLayout.isVisible = true
                    contentViewLayout.isVisible = false
                }
            }
        }
    }
}