package com.example.task16

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task15.BaseFragment
import com.example.task16.ParentRecycler.ParentAdapter
import com.example.task16.databinding.FragmentUserBinding

class UserFragment : BaseFragment<FragmentUserBinding>(
    FragmentUserBinding::inflate
) {
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val parentAdapter = ParentAdapter(emptyList())
        binding.recyclerView.apply {
            adapter = parentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }


        parentAdapter.updateList(userViewModel.fieldGroups)
    }
}
