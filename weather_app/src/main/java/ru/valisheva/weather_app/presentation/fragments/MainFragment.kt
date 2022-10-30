package ru.valisheva.weather_app.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.valisheva.weather_app.R
import ru.valisheva.weather_app.databinding.FragmentMainBinding
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.models.CurrentWeather
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.CurrWeather
import ru.valisheva.weather_app.presentation.decorators.SpaceItemDecorator
import ru.valisheva.weather_app.presentation.rv.daily.DailyAdapter
import ru.valisheva.weather_app.presentation.viewmodels.MainFragmentViewModel
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main)  {
    private lateinit var binding: FragmentMainBinding
    private lateinit var dailyAdapter: DailyAdapter
    private lateinit var cityCoordinates: CityCoordinates
    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        initObservers()
        initDailyRV()
        doBaseElementGone()
        checkPermission()
    }

    private fun initObservers(){
        viewModel.daily.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                initDailyAdapter(it)
                doBaseElementVisible()
            },onFailure = {
                Log.e("DAILY_EXCEPTION", it.message.toString())
            })
        }
        viewModel.currWeather.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { currWeather ->
                showLocalTemp(currWeather)
            },onFailure = {
                Log.e("CURRENT_WEATHER_FROM_METEO_API_EXCEPTION", it.message.toString())
            })
        }
        viewModel.currentWeather.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { currentWeather ->
                showLocalInfo(currentWeather)
            },onFailure = {
                Log.e("CURRENT_WEATHER_FROM_OPEN_API_EXCEPTION", it.message.toString())
            })
        }
        viewModel.coordinates.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                cityCoordinates = CityCoordinates(it.latitude, it.longitude)
//                viewModel.getCityName(cityCoordinates)
                viewModel.searchCurrentWeather(cityCoordinates)
                viewModel.searchDailyByCoordinates(cityCoordinates)
                viewModel.searchHourlyByCoordinates(cityCoordinates)
            },onFailure = {
                showMessage()
                Log.e("COORDINATES_EXCEPTION", it.message.toString())
            })
        }
        viewModel.city.observe((viewLifecycleOwner)){
            it?.fold(onSuccess = { it ->
                binding.tvCity.text = it
            },onFailure = {
                Log.e("CITY_NAME_EXCEPTION", it.message.toString())
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

    @SuppressLint("SetTextI18n")
    private fun showLocalTemp(hourlyWeather: CurrWeather){
        with(binding){
            tvTemp.text = hourlyWeather.currTemp.toString() + "°"
        }
    }
    private fun showLocalInfo(currentWeather: CurrentWeather){
        with(binding){
            tvDescription.text = currentWeather.descriptions
            tvCity.text = currentWeather.city
        }
    }
    private fun checkPermission(){
        if (checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED){
            viewModel.getLocation()
        }else{
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
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
                showMessage()
            }
        }

    private fun showMessage(){
        binding.tvMessage.text = requireContext().getString(R.string.permissions_not_given_message)
        binding.tvMessage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
    private fun doBaseElementVisible(){
        with(binding){
            tvCity.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
            tvTemp.visibility = View.VISIBLE
            tvHeader.visibility = View.VISIBLE
            rvWeekWeather.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            tvMessage.visibility = View.GONE
        }
    }
    private fun doBaseElementGone(){
        with(binding){
            tvCity.visibility = View.GONE
            tvDescription.visibility = View.GONE
            tvTemp.visibility = View.GONE
            tvHeader.visibility = View.GONE
            rvWeekWeather.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            tvMessage.visibility = View.GONE
        }
    }
}
