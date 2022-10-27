package ru.valisheva.weather_app.data.repositories

import ru.valisheva.weather_app.data.api.MeteoApi
import ru.valisheva.weather_app.data.api.OpenWeatherApi
import ru.valisheva.weather_app.data.api.mappers.WeatherMapper
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.models.CurrentWeather
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.CurrWeather
import ru.valisheva.weather_app.domain.repositories.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val meteoApi: MeteoApi,
    private val openWeatherApi: OpenWeatherApi,
    private val weatherMapper : WeatherMapper
) : WeatherRepository {

    override suspend fun getHourlyWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): CurrWeather {
        return weatherMapper.mapHourlyWeather(meteoApi.getHourlyWeatherByCoordinates(latitude,longitude))
    }

    override suspend fun getDailyWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): ArrayList<DailyWeather> {
        return weatherMapper.mapDailyWeather(meteoApi.getDailyWeatherByCoordinates(latitude,longitude))
    }

    override suspend fun getCityCoordinates(
        name: String
    ): CityCoordinates {
        return weatherMapper.mapCityCoordinates(openWeatherApi.getCoordinates(name))
    }

    override suspend fun getCurrentWeatherResponse(
        latitude: Double,
        longitude: Double
    ): CurrentWeather {
        return weatherMapper.mapCurrentWeather(openWeatherApi.getWeather(latitude,longitude))
    }

}
