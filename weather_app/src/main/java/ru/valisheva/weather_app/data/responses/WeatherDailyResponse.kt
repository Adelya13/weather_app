package ru.valisheva.weather_app.data.responses

import com.google.gson.annotations.SerializedName
import ru.valisheva.weather_app.data.responses.additional.Daily

data class WeatherDailyResponse(
    @SerializedName("daily")
    val daily : Daily,
)
