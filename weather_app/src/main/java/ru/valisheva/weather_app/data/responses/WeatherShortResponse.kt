package ru.valisheva.weather_app.data.responses

import com.google.gson.annotations.SerializedName
import ru.valisheva.weather_app.data.responses.additional.Coordinates

data class WeatherShortResponse(
    @SerializedName("coord")
    val coordinates: Coordinates,
)