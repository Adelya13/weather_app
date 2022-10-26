package ru.valisheva.weather_app.data.repositories

import ru.valisheva.weather_app.data.api.Api
import ru.valisheva.weather_app.data.api.mappers.WeatherMapper
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.HourlyWeather
import ru.valisheva.weather_app.domain.repositories.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api,
    private val weatherMapper : WeatherMapper
) : WeatherRepository {

    override suspend fun getHourlyWeatherByCoordinates(
        latitude: String,
        longitude: String
    ): HourlyWeather {
        return weatherMapper.mapHourlyWeather(api.getHourlyWeatherByCoordinates(latitude,longitude))
    }

    override suspend fun getDailyWeatherByCoordinates(
        latitude: String,
        longitude: String
    ): DailyWeather {
        TODO("Not yet implemented")
    }
}
