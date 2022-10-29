package ru.valisheva.weather_app.domain.usecases.location

import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.repositories.LocationRepository
import javax.inject.Inject

class GetCityNameByCoordinates  @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(coordinates : CityCoordinates): String {
        return locationRepository.getCityByCoordinates(coordinates)
    }
}