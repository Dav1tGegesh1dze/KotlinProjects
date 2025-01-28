package com.example.task20.Fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task20.UserListAdapter
import com.example.task20.ViewModels.UserViewModel
import com.example.task20.databinding.FragmentUserBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserFragment : BaseFragment<FragmentUserBinding>(
    FragmentUserBinding::inflate
) {
    private val viewModel: UserViewModel by viewModels()
    private val listAdapter = UserListAdapter()

    override fun start() {
        setUpRecycler()
        observeUserFlow()
    }

    private fun setUpRecycler() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun observeUserFlow() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.users.collectLatest { pagingData ->
                listAdapter.submitData(pagingData)
            }
        }
    }
}
