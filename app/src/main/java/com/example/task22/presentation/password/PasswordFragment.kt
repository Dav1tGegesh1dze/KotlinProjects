package com.example.task22.presentation.password

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.task22.data.PinManager
import com.example.task22.R
import kotlinx.coroutines.launch
import com.example.task22.databinding.FragmentPasswordBinding
import com.example.task22.presentation.BaseFragment

class PasswordFragment: BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate) {

    private val pinManager by lazy { PinManager(requireContext()) }

    private val viewModel: PasswordViewModel by viewModels {
        PasswordViewModelFactory(PinManager(requireContext()))
    }

    override fun start() {
        savePin()
        setupButtons()
        collectPin()
    }

    private fun setupButtons() {
        with(binding) {
            btn0.setOnClickListener { viewModel.addNumber("0") }
            btn1.setOnClickListener { viewModel.addNumber("1") }
            btn2.setOnClickListener { viewModel.addNumber("2") }
            btn3.setOnClickListener { viewModel.addNumber("3") }
            btn4.setOnClickListener { viewModel.addNumber("4") }
            btn5.setOnClickListener { viewModel.addNumber("5") }
            btn6.setOnClickListener { viewModel.addNumber("6") }
            btn7.setOnClickListener { viewModel.addNumber("7") }
            btn8.setOnClickListener { viewModel.addNumber("8") }
            btn9.setOnClickListener { viewModel.addNumber("9") }

            btnBackspace.setOnClickListener {
                viewModel.removeLastNumber()
            }
        }
    }

    private fun savePin(){
        if (pinManager.getPin() == null) {
            pinManager.savePin("0934")
        }
    }

    private fun collectPin() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.enteredPin.collect { pin ->
                    updateDots(pin.length)
                    if (pin.length == 4) {
                        if (viewModel.checkPin()) {
                            Toast.makeText(requireContext(), "PIN Correct!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Incorrect PIN", Toast.LENGTH_SHORT).show()
                            viewModel.clearPin()
                        }
                    }
                }
            }
        }
    }

    private fun updateDots(pinLength: Int) {
        with(binding) {
            dot1.setImageResource(if (pinLength >= 1) R.drawable.small_circle_filled else R.drawable.small_circle)
            dot2.setImageResource(if (pinLength >= 2) R.drawable.small_circle_filled else R.drawable.small_circle)
            dot3.setImageResource(if (pinLength >= 3) R.drawable.small_circle_filled else R.drawable.small_circle)
            dot4.setImageResource(if (pinLength >= 4) R.drawable.small_circle_filled else R.drawable.small_circle)
        }
    }
}
