package ru.valisheva.weather_app.domain.models

data class HourlyWeather(
    val currTime: String,
    val currTemp: String,
    val time : ArrayList<String>,
    val temp : ArrayList<String>,
)
