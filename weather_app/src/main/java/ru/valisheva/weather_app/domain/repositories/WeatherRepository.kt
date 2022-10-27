package ru.valisheva.weather_app.domain.repositories

import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.models.CurrentWeather
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.CurrWeather

interface WeatherRepository {
    suspend fun getHourlyWeatherByCoordinates(latitude : Double, longitude: Double) : CurrWeather
    suspend fun getDailyWeatherByCoordinates(latitude : Double, longitude: Double) : ArrayList<DailyWeather>
    suspend fun getCityCoordinates(name: String) : CityCoordinates
    suspend fun getCurrentWeatherResponse(latitude : Double, longitude: Double) : CurrentWeather
}
