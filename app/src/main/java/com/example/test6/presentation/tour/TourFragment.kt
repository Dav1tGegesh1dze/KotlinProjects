package com.example.test6.presentation.tour

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.test6.BaseFragment
import com.example.test6.R
import com.example.test6.Resource
import com.example.test6.TourPagerAdapter
import com.example.test6.databinding.FragmentTourMotionBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TourFragment : BaseFragment<FragmentTourMotionBinding>(FragmentTourMotionBinding::inflate) {

    private val viewModel: TourViewModel by viewModels()
    private val tourAdapter = TourPagerAdapter()
    private val snapHelper = LinearSnapHelper()
    private var isScalingApplied = false

    override fun start() {
        setupRecyclerView()
        observeData()
        setupScrollListener()
    }

    private fun setupRecyclerView() {
        val screenWidth = resources.displayMetrics.widthPixels
        val cardWidth = resources.getDimensionPixelSize(R.dimen.card_width)
        val horizontalPadding = (screenWidth - cardWidth) / 2

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = tourAdapter
            clipChildren = false
            clipToPadding = false


            setPadding(horizontalPadding, 0, horizontalPadding, 0)

            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    val margin = resources.getDimensionPixelOffset(R.dimen.page_margin)
                    outRect.left = margin
                    outRect.right = margin
                }
            })

            snapHelper.attachToRecyclerView(this)
        }
    }




    private fun setupScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                applyScaleEffect()
                updateIndicator()
            }
        })
    }

    private fun applyScaleEffect() {
        val recyclerView = binding.recyclerView
        val centerX = (recyclerView.left + recyclerView.right) / 2f
        val scaleFactor = 0.85f

        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            val childCenter = (child.left + child.right) / 2f
            val distanceFromCenter = Math.abs(centerX - childCenter)
            val maxDistance = recyclerView.width / 2f

            val scale = Math.max(
                scaleFactor,
                1f - (distanceFromCenter / maxDistance) * (1f - scaleFactor)
            )

            child.scaleX = scale
            child.scaleY = scale

            child.alpha = Math.max(0.6f, scale)
        }
    }

    private fun updateIndicator() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        val centerPosition = layoutManager.findFirstVisibleItemPosition()

        if (centerPosition != RecyclerView.NO_POSITION) {
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(centerPosition))
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toursState.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            if (tourAdapter.itemCount == 0) {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            tourAdapter.submitList(resource.data) {
                                if (!isScalingApplied) {
                                    binding.recyclerView.post {
                                        applyScaleEffect()
                                        isScalingApplied = true
                                    }
                                }
                            }
                            setupIndicator(resource.data.size)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Snackbar.make(binding.root, resource.errorMessage, Snackbar.LENGTH_LONG).show()
                        }
                        else -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setupIndicator(itemCount: Int) {
        binding.tabLayout.apply {
            visibility = if (itemCount > 0) View.VISIBLE else View.GONE
            removeAllTabs()
            repeat(itemCount) {
                addTab(newTab().apply {
                    setText("•")
                    view.setPadding(8, 0, 8, 0)
                })
            }

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let { position ->
                        binding.recyclerView.smoothScrollToPosition(position)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
}