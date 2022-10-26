package ru.valisheva.weather_app.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.valisheva.weather_app.domain.models.HourlyWeather
import ru.valisheva.weather_app.domain.usecases.GetHourlyWeatherByCoordinates
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getHourlyWeatherByCoordinates: GetHourlyWeatherByCoordinates
) : ViewModel(){

    private var _hourly: MutableLiveData<Result<ArrayList<HourlyWeather>>> = MutableLiveData()
    val hourly: LiveData<Result<ArrayList<HourlyWeather>>> = _hourly
}
