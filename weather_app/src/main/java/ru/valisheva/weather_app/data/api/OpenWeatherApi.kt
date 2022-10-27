package ru.valisheva.weather_app.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.valisheva.weather_app.data.responses.CurrentWeatherResponse
import ru.valisheva.weather_app.data.responses.WeatherShortResponse


interface OpenWeatherApi {

    @GET("weather")
    suspend fun getCoordinates(
        @Query("q") city: String
    ): WeatherShortResponse

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): CurrentWeatherResponse

}