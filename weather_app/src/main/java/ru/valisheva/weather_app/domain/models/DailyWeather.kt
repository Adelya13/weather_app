package ru.valisheva.weather_app.domain.models

data class DailyWeather (
    val weekday : String,
    val minTemp: Int,
    val maxTemp: Int,
    val img: Int? = null,
)

