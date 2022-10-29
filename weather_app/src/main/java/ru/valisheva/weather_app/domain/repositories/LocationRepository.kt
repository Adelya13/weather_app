package ru.valisheva.weather_app.domain.repositories

import ru.valisheva.weather_app.domain.models.CityCoordinates


interface LocationRepository {
    suspend fun getLocation(): CityCoordinates
    fun getDefaultLocation(): CityCoordinates
    fun getCityByCoordinates(coordinates : CityCoordinates) : String
}