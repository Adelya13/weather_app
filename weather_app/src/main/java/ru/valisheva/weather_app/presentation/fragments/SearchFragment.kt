package ru.valisheva.weather_app.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.valisheva.weather_app.R
import ru.valisheva.weather_app.databinding.FragmentSearchBinding
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.models.CurrWeather
import ru.valisheva.weather_app.domain.models.CurrentWeather
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.presentation.decorators.SpaceItemDecorator
import ru.valisheva.weather_app.presentation.rv.daily.DailyAdapter
import ru.valisheva.weather_app.presentation.viewmodels.SearchFragmentViewModel

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var dailyAdapter: DailyAdapter
    private lateinit var cityCoordinates: CityCoordinates
    private val viewModel: SearchFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        initObservers()
        initDailyRV()
        initSV()
        doBaseElementGone()
        stopProgress()
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers(){
        viewModel.daily.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                initDailyAdapter(it)
                stopProgress()
                doBaseElementVisible()
            },onFailure = {
                Log.e("DAILY_EXCEPTION", it.message.toString())

            })
        }
        viewModel.currWeather.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                showLocalTemp(it)
            },onFailure = {
                Log.e("CURRENT_WEATHER_FROM_METEO_API_EXCEPTION", it.message.toString())
            })
        }
        viewModel.currentWeather.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                showDescription(it)
            },onFailure = {
                Log.e("CURRENT_WEATHER_EXCEPTION_FROM_OPEN_API", it.message.toString())
            })
        }
        viewModel.coordinates.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                cityCoordinates = CityCoordinates(it.latitude, it.longitude)
                viewModel.searchCurrentWeather(cityCoordinates)
                viewModel.searchDailyByCoordinates(cityCoordinates)
                viewModel.searchHourlyByCoordinates(cityCoordinates)
            },onFailure = {
                binding.tvMessage.text = context?.getString(R.string.not_found_message)
                doBaseElementGone()
                stopProgress()
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
    private fun initSV(){
        with(binding){

            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                @SuppressLint("SetTextI18n")
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        doBaseElementGone()
                        doProgress()
                        viewModel.getCoordinatesByCityName(query)
                    }else{
                        tvMessage.text = context?.getString(R.string.message)
                        doBaseElementGone()
                    }
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
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
            tvTemp.text = hourlyWeather.currTemp.toString() +  "°"
        }
    }
    private fun showDescription(currentWeather: CurrentWeather){
        binding.tvDescription.text = currentWeather.descriptions
        binding.tvCity.text = currentWeather.city
    }

    private fun doProgress(){
        binding.progressBar.visibility = View.VISIBLE
        binding.tvMessage.visibility = View.GONE
    }
    private fun stopProgress(){
        binding.progressBar.visibility = View.GONE
    }
    private fun doBaseElementVisible(){
        with(binding){
            tvCity.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
            tvTemp.visibility = View.VISIBLE
            tvHeader.visibility = View.VISIBLE
            rvWeekWeather.visibility = View.VISIBLE
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
            tvMessage.visibility = View.VISIBLE
        }
    }
}
