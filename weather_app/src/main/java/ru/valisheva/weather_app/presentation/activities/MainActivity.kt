package ru.valisheva.weather_app.presentation.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.valisheva.weather_app.R
import ru.valisheva.weather_app.databinding.ActivityMainBinding
import ru.valisheva.weather_app.presentation.ViewPagerAdapter
import ru.valisheva.weather_app.presentation.fragments.MainFragment
import ru.valisheva.weather_app.presentation.fragments.SearchFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }
        val adapter= ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MainFragment(), applicationContext.getString(R.string.general))
        adapter.addFragment(SearchFragment(),applicationContext.getString(R.string.search))
        binding.viewPager.adapter=adapter
        binding.tbLayout.setupWithViewPager(binding.viewPager)
    }
}
