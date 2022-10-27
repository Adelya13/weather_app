package ru.valisheva.weather_app.data.responses

import com.google.gson.annotations.SerializedName
import ru.valisheva.weather_app.data.responses.additional.Descriptions

data class CurrentWeatherResponse(
    @SerializedName("name")
    val city: String,
    @SerializedName("weather")
    val descriptions: List<Descriptions>
)