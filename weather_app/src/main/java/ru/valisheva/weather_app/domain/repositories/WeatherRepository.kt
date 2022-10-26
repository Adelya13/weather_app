package ru.valisheva.weather_app.domain.repositories

import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.HourlyWeather

interface WeatherRepository {
    suspend fun getHourlyWeatherByCoordinates(latitude : String, longitude: String) : HourlyWeather
    suspend fun getDailyWeatherByCoordinates(latitude : String, longitude: String) : DailyWeather
}
