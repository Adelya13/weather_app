package ru.valisheva.weather_app.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.HourlyWeather
import ru.valisheva.weather_app.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetWeekWeatherByCoordinates  @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(latitude : String, longitude: String ): DailyWeather {
        return withContext(dispatcher){
            weatherRepository.getDailyWeatherByCoordinates(latitude, longitude)
        }
    }
}
