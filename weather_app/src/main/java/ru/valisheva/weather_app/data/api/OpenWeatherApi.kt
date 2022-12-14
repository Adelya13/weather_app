package ru.valisheva.weather_app.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.valisheva.weather_app.data.responses.CurrentWeatherResponse
import ru.valisheva.weather_app.data.responses.WeatherShortResponse


interface OpenWeatherApi {

    @GET("weather?lang=ru")
    suspend fun getCoordinates(
        @Query("q") city: String
    ): WeatherShortResponse

    @GET("weather?lang=ru")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): CurrentWeatherResponse

}
