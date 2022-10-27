package ru.valisheva.weather_app.data.responses

import com.google.gson.annotations.SerializedName
import ru.valisheva.weather_app.data.responses.additional.CurrentWeather

data class WeatherResponse(
    @SerializedName("current_weather")
    val currentWeather: CurrentWeather,
)
