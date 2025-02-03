package com.example.test5.fragment


import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.test5.UserAdapter
import com.example.test5.UserRepository
import com.example.test5.viewModel.UserViewModelFactory
import com.example.test5.databinding.FragmentWelcomeBinding
import com.example.test5.local.AppDatabase
import com.example.test5.server.NetworkModule
import com.example.test5.viewModel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate
) {
    private val viewModel: UserViewModel by viewModels {
        val database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()

        val repository = UserRepository(
            api = NetworkModule.apiService,
            userDao = database.userDao()
        )

        UserViewModelFactory(repository, requireContext())
    }

    private val userAdapter = UserAdapter()

    override fun start() {
        setupRecyclerView()
        observeData()
        viewModel.loadUsers()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        binding.progressBar.isVisible = isLoading
                        delay(1000)
                    }
                }

                launch {
                    viewModel.isOfflineData.collect { isOffline ->
                        binding.offlineMessage.isVisible = isOffline
                    }
                }

                launch {
                    viewModel.users.collect { users ->
                        userAdapter.submitList(users)
                    }
                }

                launch {
                    viewModel.error.collect { errorMessage ->
                        Toast.makeText(
                            requireContext(),
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}