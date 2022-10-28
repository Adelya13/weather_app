package ru.valisheva.weather_app.presentation.fragments

import android.annotation.SuppressLint
import android.opengl.Visibility
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
                binding.tvMessage.text = "City not found!"
                stopProgress()
                doBaseElementGone()
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
                        tvMessage.text = "Please enter the city name"
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

    private fun showLocalTemp(hourlyWeather: CurrWeather){
        with(binding){
            tvTemp.text = hourlyWeather.currTemp.toString()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun showLocalInfo(currentWeather: CurrentWeather){
        with(binding){
            tvCity.text = currentWeather.city + "°"
            tvDescription.text = currentWeather.descriptions
        }
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
    private fun doProgress(){
        binding.progressBar.visibility = View.VISIBLE
        binding.tvMessage.visibility = View.GONE
    }
    private fun stopProgress(){
        binding.progressBar.visibility = View.GONE
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
