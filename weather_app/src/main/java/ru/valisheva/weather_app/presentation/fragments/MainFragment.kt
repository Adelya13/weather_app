package ru.valisheva.weather_app.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.valisheva.weather_app.R
import ru.valisheva.weather_app.databinding.FragmentMainBinding
import ru.valisheva.weather_app.domain.models.CurrentWeather
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.CurrWeather
import ru.valisheva.weather_app.presentation.decorators.SpaceItemDecorator
import ru.valisheva.weather_app.presentation.rv.daily.DailyAdapter
import ru.valisheva.weather_app.presentation.viewmodels.MainFragmentViewModel

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main)  {
    private lateinit var binding: FragmentMainBinding
    private lateinit var dailyAdapter: DailyAdapter
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        initAll()
        getLocation()
    }

    private fun initAll(){
        initObservers()
        initDailyRV()

    }
    private fun initObservers(){
        viewModel.daily.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                initDailyAdapter(it)
            },onFailure = {
                Log.e("DAILY_EXCEPTION", it.message.toString())
            })
        }
        viewModel.currWeather.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                showLocalTemp(it)
            },onFailure = {
                Log.e("CURRENT_WEATHER_EXCEPTION", it.message.toString())
            })
        }
        viewModel.currentWeather.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                showLocalInfo(it)
            },onFailure = {
                Log.e("CURRENT_WEATHER_EXCEPTION", it.message.toString())
            })
        }
        viewModel.coordinates.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                viewModel.searchCurrentWeather(it.latitude,it.longitude)
                viewModel.searchDailyByCoordinates(it.latitude,it.longitude)
                viewModel.searchHourlyByCoordinates(it.latitude,it.longitude)
            },onFailure = {
                Log.e("COORDINATES_EXCEPTION", it.message.toString())
            })
        }
    }
    private fun initDailyAdapter(dailyWeatherList: ArrayList<DailyWeather>){
        dailyAdapter = DailyAdapter(dailyWeatherList)
        binding.rvWeekWeather.apply {
            adapter = dailyAdapter
        }
    }

    private fun initDailyRV(){
        val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val spacing = SpaceItemDecorator(requireContext())
        binding.rvWeekWeather.run{
            addItemDecoration(decorator)
            addItemDecoration(spacing)
        }
    }

    private fun showLocalTemp(hourlyWeather: CurrWeather){
        with(binding){
            tvTemp.text = hourlyWeather.currTemp.toString()
        }
    }
    private fun showLocalInfo(currentWeather: CurrentWeather){
        with(binding){
            tvCity.text = currentWeather.city
            tvDescription.text = currentWeather.descriptions
        }
    }

    @SuppressLint("MissingPermission")
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (
                it[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                viewModel.getLocation()
            }else{
                viewModel.getDefaultLocation()
            }
        }

    private fun getLocation() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}
