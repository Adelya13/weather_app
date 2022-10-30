package ru.valisheva.weather_app.domain.repositories

import ru.valisheva.weather_app.domain.models.CityCoordinates


interface LocationRepository {
    suspend fun getLocation(): CityCoordinates
    suspend fun getCityByCoordinates(coordinates : CityCoordinates) : String
}
