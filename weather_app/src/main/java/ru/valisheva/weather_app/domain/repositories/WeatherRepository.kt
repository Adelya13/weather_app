package ru.valisheva.weather_app.domain.repositories

import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.models.CurrentWeather
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.CurrWeather

interface WeatherRepository {
    suspend fun getHourlyWeatherByCoordinates(cityCoordinates: CityCoordinates) : CurrWeather
    suspend fun getDailyWeatherByCoordinates(cityCoordinates: CityCoordinates) : ArrayList<DailyWeather>
    suspend fun getCityCoordinates(name: String) : CityCoordinates
    suspend fun getCurrentWeatherResponse(cityCoordinates: CityCoordinates) : CurrentWeather
}
