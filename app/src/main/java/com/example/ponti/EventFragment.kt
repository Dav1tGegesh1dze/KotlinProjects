package com.example.ponti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.ponti.databinding.FragmentEventBinding
import com.example.ponti.domain.Category
import com.example.ponti.ui.adapter.CategoryAdapter
import com.example.ponti.ui.adapter.EventPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding>(FragmentEventBinding::inflate) {

    private val viewModel: EventViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var eventPagerAdapter: EventPagerAdapter

    override fun start() {
        setupObservers()
        setupViewPager()
    }

    private fun setupObservers() {
        // Change LiveData.observe to Flow.collect, using viewLifecycleOwner's lifecycleScope
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle ensures collection only happens in specific lifecycle states
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Collect from categories flow
                viewModel.categories.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            setupCategoryRecyclerView(resource.data)
                        }
                        is Resource.Error -> {
                            hideLoading()
                            showToast(resource.errorMessage)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Collect from events flow
                viewModel.events.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            if (!isLoading()) {
                                showLoading()
                            }
                        }
                        is Resource.Success -> {
                            hideLoading()
                        }
                        is Resource.Error -> {
                            hideLoading()
                            showToast(resource.errorMessage)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Collect from filteredEvents flow
                viewModel.filteredEvents.collect { events ->
                    if (!::eventPagerAdapter.isInitialized) {
                        eventPagerAdapter = EventPagerAdapter(events)
                        binding.viewPager.adapter = eventPagerAdapter
                    } else {
                        eventPagerAdapter.updateEvents(events)
                    }
                }
            }
        }
    }

    private fun isLoading(): Boolean {
        // You might want to implement this method in your BaseFragment
        return false
    }

    private fun setupCategoryRecyclerView(categories: List<Category>) {
        categoryAdapter = CategoryAdapter(categories) { category ->
            viewModel.selectCategory(category)
        }
        binding.rvCategories.adapter = categoryAdapter
    }

    private fun setupViewPager() {
        // Set up ViewPager2 with tinder-like swiping
        binding.viewPager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.9f + r * 0.15f
            }

            setPageTransformer(compositePageTransformer)

            // Get the inner RecyclerView of ViewPager2
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                // Reduce padding to make cards wider
                val padding = resources.displayMetrics.widthPixels / 15  // Changed from /6 to /12
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
        }
    }
}