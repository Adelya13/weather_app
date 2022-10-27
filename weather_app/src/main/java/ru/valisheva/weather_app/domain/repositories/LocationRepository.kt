package ru.valisheva.weather_app.domain.repositories

import ru.valisheva.weather_app.domain.models.CityCoordinates


interface LocationRepository {
    fun getLocation(): CityCoordinates
    fun getDefaultLocation(): CityCoordinates
}