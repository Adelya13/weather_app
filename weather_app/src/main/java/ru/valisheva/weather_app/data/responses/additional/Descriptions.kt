package ru.valisheva.weather_app.data.responses.additional

import com.google.gson.annotations.SerializedName

data class Descriptions(
    @SerializedName("description")
    val description : String,

)