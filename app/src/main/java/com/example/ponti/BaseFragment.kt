package com.example.ponti

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null  // Changed from *binding to _binding
    protected val binding: VB
        get() = _binding!!

    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)  // Changed from *binding to _binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadingDialog()
        start()
    }

    private fun setupLoadingDialog() {
        val progressBar = ProgressBar(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,  // Fixed from WRAP*CONTENT
                ViewGroup.LayoutParams.WRAP_CONTENT   // Fixed from WRAP*CONTENT
            )
        }

        loadingDialog = AlertDialog.Builder(requireContext())
            .setView(progressBar)
            .setCancelable(false)
            .create()
            .apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

    protected fun showLoading() {
        loadingDialog?.show()
    }

    protected fun hideLoading() {
        loadingDialog?.dismiss()
    }

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    abstract fun start()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        loadingDialog = null
    }
}