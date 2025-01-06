package com.example.test3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test3.databinding.FragmentGameSettingsBinding

class GameSettingsFragment : Fragment() {

    private var _binding: FragmentGameSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.firstButton.setOnClickListener {
            navigateToGameFragment(3)
        }
        binding.secondButton.setOnClickListener {
            navigateToGameFragment(4)
        }
        binding.thirdButton.setOnClickListener {
            navigateToGameFragment(5)
        }
    }

    private fun navigateToGameFragment(gridSize: Int) {
        val fragment = GameFragment().apply {
            arguments = Bundle().apply {
                putInt("gridSize", gridSize)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
