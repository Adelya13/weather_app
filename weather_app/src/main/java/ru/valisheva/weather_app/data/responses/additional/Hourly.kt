package ru.valisheva.weather_app.data.responses.additional

import com.google.gson.annotations.SerializedName

data class Hourly (
    @SerializedName("time")
    val time : ArrayList<String>,
    @SerializedName("temperature_2m")
    val temp : ArrayList<String>,
)
