package com.example.task14.ParentFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task14.ActiveOrderFragment.ActiveOrdersFragment
import com.example.task14.CompletedOrderFragment.CompletedOrdersFragment
import com.example.task14.databinding.FragmentOrdersParentBinding
import com.google.android.material.tabs.TabLayoutMediator

class OrdersParentFragment : Fragment() {

    private lateinit var binding: FragmentOrdersParentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersParentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  viewPager2 - tabLayout
        val adapter = OrdersPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Active" else "Completed"
        }.attach()
    }

    private inner class OrdersPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return if (position == 0) ActiveOrdersFragment() else CompletedOrdersFragment()
        }
    }
}
