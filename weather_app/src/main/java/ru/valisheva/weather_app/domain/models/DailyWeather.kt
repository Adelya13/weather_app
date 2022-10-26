package ru.valisheva.weather_app.domain.models

data class DailyWeather (
    val time : ArrayList<String>,
    val tempMax: ArrayList<String>,
    val tempMin: ArrayList<String>,
)
