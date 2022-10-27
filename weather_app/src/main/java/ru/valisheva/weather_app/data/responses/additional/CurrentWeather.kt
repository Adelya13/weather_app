package ru.valisheva.weather_app.data.responses.additional

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("temperature")
    val temp: Double,
    @SerializedName("time")
    val time: String,
)
