package ru.valisheva.weather_app.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetCityCoordinates @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(name: String): CityCoordinates {
        return withContext(dispatcher){
            weatherRepository.getCityCoordinates(name)
        }
    }
}