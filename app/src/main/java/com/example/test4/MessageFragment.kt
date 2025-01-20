package com.example.test4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.task17.BaseFragment
import com.example.test4.databinding.FragmentMessageBinding

class MessageFragment : BaseFragment<FragmentMessageBinding>(
    FragmentMessageBinding::inflate
) {
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageViewModel: MessageViewModel
    private var allMessages: List<Message> = emptyList()

    // coroutine
    private val fragmentScope = CoroutineScope(Dispatchers.Main + Job())
    private var searchJob: Job? = null

    override fun start() {
        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        messageAdapter = MessageAdapter()

        binding.recyclerView.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        allMessages = messageViewModel.getMessages()
        messageAdapter.submitList(allMessages)


        binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString().trim()
            searchJob?.cancel()
            searchJob = filterMessages(searchText)
        }
    }

    private fun filterMessages(query: String): Job = fragmentScope.launch {
        binding.progressBar.visibility = View.VISIBLE

        withContext(Dispatchers.Default) {
            delay(1000)
            val filteredMessages = if (query.isEmpty()) {
                allMessages
            } else {
                allMessages.filter {
                    it.owner.contains(query, ignoreCase = true)
                }
            }
            withContext(Dispatchers.Main) {
                messageAdapter.submitList(filteredMessages)

                }
        }


        binding.progressBar.visibility = View.GONE

    }


}