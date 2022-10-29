package ru.valisheva.weather_app.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.models.CurrWeather
import ru.valisheva.weather_app.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetHourlyWeatherByCoordinates  @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(cityCoordinates: CityCoordinates): CurrWeather {
        return withContext(dispatcher){
            weatherRepository.getHourlyWeatherByCoordinates(cityCoordinates)
        }
    }
}
