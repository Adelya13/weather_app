package ru.valisheva.weather_app.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.models.CurrentWeather
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.CurrWeather
import ru.valisheva.weather_app.domain.usecases.GetCityCoordinates
import ru.valisheva.weather_app.domain.usecases.GetCurrentWeatherResponse
import ru.valisheva.weather_app.domain.usecases.GetHourlyWeatherByCoordinates
import ru.valisheva.weather_app.domain.usecases.GetDailyWeatherByCoordinates
import ru.valisheva.weather_app.domain.usecases.location.GetCityNameByCoordinates
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val getHourlyWeatherByCoordinates: GetHourlyWeatherByCoordinates,
    private val getDailyWeatherByCoordinates: GetDailyWeatherByCoordinates,
    private val getCityCoordinates: GetCityCoordinates,
    private val getCurrentWeatherResponse: GetCurrentWeatherResponse,
) : ViewModel(){

    private var _currWeather: MutableLiveData<Result<CurrWeather>> = MutableLiveData()
    val currWeather: LiveData<Result<CurrWeather>> = _currWeather

    private var _daily: MutableLiveData<Result<ArrayList<DailyWeather>>> = MutableLiveData()
    val daily: LiveData<Result<ArrayList<DailyWeather>>> = _daily

    private var _currentWeather: MutableLiveData<Result<CurrentWeather>> = MutableLiveData()
    val currentWeather: LiveData<Result<CurrentWeather>> = _currentWeather

    private var _coordinates: MutableLiveData<Result<CityCoordinates>> = MutableLiveData()
    val coordinates: LiveData<Result<CityCoordinates>> = _coordinates



    fun getCoordinatesByCityName(name: String){
        viewModelScope.launch {
            try {
                val coordinates = getCityCoordinates(name)
                _coordinates.value = Result.success(coordinates)
            }catch (ex: Exception) {
                _coordinates.value = Result.failure(ex)
            }
        }
    }
    fun searchHourlyByCoordinates(coordinates: CityCoordinates) {
        viewModelScope.launch {
            try {
                val hourly = getHourlyWeatherByCoordinates(coordinates)
                _currWeather.value = Result.success(hourly)
            }catch (ex: Exception) {
                _currWeather.value = Result.failure(ex)
            }
        }
    }
    fun searchDailyByCoordinates(coordinates: CityCoordinates) {
        viewModelScope.launch {
            try {
                val daily = getDailyWeatherByCoordinates(coordinates)
                _daily.value = Result.success(daily)
            } catch (ex: Exception) {
                _daily.value = Result.failure(ex)
            }
        }
    }

    fun searchCurrentWeather(coordinates: CityCoordinates){
        viewModelScope.launch {
            try {
                val currentWeather = getCurrentWeatherResponse(coordinates)
                _currentWeather.value = Result.success(currentWeather)
            } catch (ex: Exception) {
                _currentWeather.value = Result.failure(ex)
            }
        }
    }
}
