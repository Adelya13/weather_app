package ru.valisheva.weather_app.data.responses

import com.google.gson.annotations.SerializedName
import ru.valisheva.weather_app.data.responses.additional.CurrentWeather
import ru.valisheva.weather_app.data.responses.additional.Hourly

data class WeatherHourlyResponse(
    @SerializedName("hourly")
    val hourly : Hourly,
    @SerializedName("current_weather")
    val currentWeather: CurrentWeather,
)
