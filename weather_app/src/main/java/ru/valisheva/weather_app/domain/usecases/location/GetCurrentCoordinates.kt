package ru.valisheva.weather_app.domain.usecases.location

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.repositories.LocationRepository
import javax.inject.Inject

class GetCurrentCoordinates  @Inject constructor(
    private val locationRepository: LocationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(): CityCoordinates {
        return withContext(dispatcher){
            locationRepository.getLocation()
        }
    }
}
