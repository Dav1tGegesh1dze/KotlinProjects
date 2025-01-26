package com.example.task18.Fragments

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task18.Clients.AnotherRetrofitClient
import com.example.task18.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val usersListAdapter = UsersListAdapter()

    override fun start() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersListAdapter
        }

        fetchUsers()
    }

    private fun fetchUsers() {
        lifecycleScope.launch {
            try {
                val response = AnotherRetrofitClient.retrofit.getUsers()
                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        println("Fetched users: ${userResponse.data}")
                        usersListAdapter.submitList(userResponse.data)
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
