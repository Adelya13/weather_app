package ru.valisheva.weather_app.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import ru.valisheva.weather_app.R
import ru.valisheva.weather_app.databinding.FragmentBaseBinding
import ru.valisheva.weather_app.presentation.ViewPagerAdapter

class BaseFragment : Fragment(R.layout.fragment_base) {

    private lateinit var binding: FragmentBaseBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBaseBinding.bind(view)
        val adapter= ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(MainFragment(),"Main")
        adapter.addFragment(SearchFragment(),"Search")
        binding.viewPager.adapter=adapter
        binding.tbLayout.setupWithViewPager(binding.viewPager)
    }
}