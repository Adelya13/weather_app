package ru.valisheva.weather_app.data.responses.additional

import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("time")
    val time : ArrayList<String>,
    @SerializedName("temperature_2m_max")
    val tempMax : ArrayList<Double>,
    @SerializedName("temperature_2m_min")
    val tempMin : ArrayList<Double>,
)
