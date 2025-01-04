package com.example.task13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task13.databinding.FragmentMessageBinding

class Message : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setUpSendButton()
    }

    private fun setUpSendButton(){
        binding.sendButton.setOnClickListener{
            setUpButtonLogic()
        }
    }

    private fun setUpButtonLogic(){
        val messageText = binding.inputMessage.text.toString()
        if (messageText.isNotBlank()) {
            addMessage(messageText)
            binding.inputMessage.text.clear()
        }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }
    }

    private fun addMessage(messageText: String) {
        val newMessage = MessageDetails(
            id = messageAdapter.currentList.size + 1,
            message = messageText,
            time = System.currentTimeMillis()
        )

        val updatedList = messageAdapter.currentList.toMutableList()
        updatedList.add(newMessage)
        messageAdapter.submitList(updatedList)
        binding.recyclerView.scrollToPosition(updatedList.size - 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}