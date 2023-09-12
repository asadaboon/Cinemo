package com.example.cinemo.presentation.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemo.databinding.FragmentFinderSceneBinding
import com.example.cinemo.domain.model.MovieItemResponse
import com.example.cinemo.domain.model.MovieResponse
import com.example.cinemo.presentation.ui.adapter.MovieAdapter
import com.example.cinemo.presentation.viewmodel.MovieViewModel
import com.example.cinemo.utils.ApiStatus
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FinderSceneFragment : Fragment() {

    private lateinit var binding: FragmentFinderSceneBinding
    private val viewModel: MovieViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinderSceneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObserve()
    }

    private fun setUpView() {
        binding.apply {
            searchMovieEditText.setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    context?.let { mContext ->
                        val imm = mContext.getSystemService(
                            AppCompatActivity.INPUT_METHOD_SERVICE
                        ) as InputMethodManager?
                        imm?.hideSoftInputFromWindow(textView.windowToken, 0)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            searchMovieEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(str: Editable?) {
                    val searchText = str.toString().lowercase(
                        Locale.getDefault()
                    )
                    viewModel.searchMovie(keyWord = searchText)
                }

                override fun beforeTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // do something
                }

                override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // do something
                }
            })
        }
    }

    private fun setUpObserve() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result.status) {
                    ApiStatus.LOADING -> {
                        loadMovieProgressBar.isVisible = true
                    }
                    ApiStatus.SUCCESS -> {
                        loadMovieProgressBar.isVisible = false
                        setUpItemView(result.data)
                    }
                    ApiStatus.ERROR -> {
                        loadMovieProgressBar.isVisible = false
                    }
                }
            }
        }

        viewModel.updateMovieLiveData.observe(viewLifecycleOwner) { updatePosition ->
            movieAdapter.notifyItemChanged(updatePosition)
        }

        viewModel.searchMovieFavoriteListLiveData.observe(viewLifecycleOwner) { searchList ->
            if (searchList.second) {
                setUpItemView(searchList.first)
            }
        }
    }

    private fun setUpItemView(response: MovieResponse?) {
        binding.apply {
            if (response?.movies?.isNotEmpty() == true) {
                emptyViewLayout.isVisible = false
                contentViewLayout.isVisible = true

                movieAdapter = MovieAdapter(response.movies)
                movieRecyclerView.setHasFixedSize(true)
                movieRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                movieRecyclerView.adapter = movieAdapter

            } else {
                emptyViewLayout.isVisible = true
                contentViewLayout.isVisible = false
            }
        }

        movieAdapter.setOnClickListener(object : MovieAdapter.OnClickListener {
            override fun onClick(position: Int, item: MovieItemResponse) {
                val action =
                    FinderSceneFragmentDirections.actionMovieListToMovieDetailFragment(
                        item,
                        position
                    )
                this@FinderSceneFragment.findNavController().navigate(action)
            }
        })
    }
}