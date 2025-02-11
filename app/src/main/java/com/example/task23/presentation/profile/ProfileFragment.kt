package com.example.task23.presentation.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task23.BaseFragment
import com.example.task23.data.local.ProfileDatabase
import com.example.task23.data.remote.ProfileRepository
import com.example.task23.data.remote.RetrofitClient
import com.example.task23.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.Factory(
            ProfileRepository(ProfileDatabase.getDatabase(requireContext()), RetrofitClient.apiService)
        )
    }

    private val profileAdapter = ProfileAdapter()

    override fun start() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = profileAdapter
        }

        lifecycleScope.launch {
            viewModel.profiles.collectLatest { pagingData ->
                profileAdapter.submitData(pagingData)
            }
        }

        profileAdapter.addLoadStateListener { loadState ->
            binding.progressBar.visibility = if (loadState.source.refresh is LoadState.Loading) View.VISIBLE else View.GONE
        }
    }
}
