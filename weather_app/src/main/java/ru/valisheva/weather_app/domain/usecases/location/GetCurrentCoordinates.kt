package ru.valisheva.weather_app.domain.usecases.location

import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.repositories.LocationRepository
import javax.inject.Inject

class GetCurrentCoordinates  @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke(): CityCoordinates {
        return locationRepository.getLocation()
    }
}