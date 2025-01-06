package com.example.test3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.test3.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var gridSize: Int = 3 //Defaukt
    private lateinit var adapter: RecyclerViewAdapter
    private var currentPlayer: Char = 'X'
    private var isGameActive: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridSize = arguments?.getInt("gridSize", 3) ?: 3
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val items = createGameItems(gridSize)
        adapter = RecyclerViewAdapter(items) { gameDataClass ->
            if (isGameActive && gameDataClass.mark == ' ') {
                gameDataClass.mark = currentPlayer
                adapter.notifyItemChanged(gameDataClass.id)

                if (checkForWin()) {
                    isGameActive = false
                    showEndGameMessage("$currentPlayer wins!")
                } else if (isBoardFull()) {
                    isGameActive = false
                    showEndGameMessage("draw!")
                } else {
                    currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, gridSize)
            adapter = this@GameFragment.adapter
        }
    }

    private fun createGameItems(gridSize: Int): List<GameDataClass> {
        val items = mutableListOf<GameDataClass>()
        for (i in 0 until gridSize * gridSize) {
            // vals
            items.add(GameDataClass(id = i, mark = ' ', gameImage = R.drawable.ic_launcher_background))
        }
        return items
    }

    private fun checkForWin(): Boolean {
        val grid = gridSize
        val marks = adapter.getItems().map { it.mark }

        // row checking
        for (i in 0 until grid) {
            if ((0 until grid).all { marks[i * grid + it] == currentPlayer }) return true
        }

        // column checking
        for (i in 0 until grid) {
            if ((0 until grid).all { marks[it * grid + i] == currentPlayer }) return true
        }

        // diagonals checking
        if ((0 until grid).all { marks[it * grid + it] == currentPlayer }) return true
        if ((0 until grid).all { marks[it * grid + (grid - 1 - it)] == currentPlayer }) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        return adapter.getItems().all { it.mark != ' ' }
    }

    private fun showEndGameMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        binding.root.postDelayed({ resetGame() }, 3000)
    }

    //reset affter end
    private fun resetGame() {
        isGameActive = true
        currentPlayer = 'X'
        adapter.getItems().forEach { it.mark = ' ' }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_GRID_SIZE = "gridSize"

        fun newInstance(gridSize: Int): GameFragment {
            val fragment = GameFragment()
            val args = Bundle().apply {
                putInt(ARG_GRID_SIZE, gridSize)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
