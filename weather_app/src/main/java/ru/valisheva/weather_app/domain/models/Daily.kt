package ru.valisheva.weather_app.domain.models

data class Daily(
    val time : String,
    val temp: Pair<Int,Int>
)