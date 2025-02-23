package com.example.task27.presentation.home

import android.graphics.Rect
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task27.presentation.home.adapter.PostAdapter
import com.example.task27.R
import com.example.task27.data.models.Resource
import com.example.task27.presentation.home.adapter.StoryAdapter
import com.example.task27.databinding.FragmentHomeBinding
import com.example.task27.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private val storyAdapter = StoryAdapter()
    private val postAdapter = PostAdapter()

    override fun start() {
        setupRecyclerViews()
        observeData()
        viewModel.fetchData()
    }

    private fun setupRecyclerViews() {
        binding.apply {
            rvStories.apply {
                adapter = storyAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.right = resources.getDimensionPixelSize(R.dimen.story_item_spacing)
                    }
                })
            }

            rvPosts.apply {
                adapter = postAdapter
                layoutManager = LinearLayoutManager(context)

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()

                        rvStories.visibility = if (firstVisibleItem > 0) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                    }
                })
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.stories.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            storyAdapter.updateData(result.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                requireContext(),
                                result.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            launch {
                viewModel.posts.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            postAdapter.updateData(result.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(
                                requireContext(),
                                result.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}