package ru.valisheva.weather_app.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.valisheva.weather_app.data.responses.WeatherDailyResponse
import ru.valisheva.weather_app.data.responses.WeatherResponse

interface MeteoApi{

    @GET("forecast?current_weather=true&hourly=temperature_2m,weathercode")
    suspend fun getHourlyWeatherByCoordinates(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double
    ): WeatherResponse

    @GET("forecast?daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FMoscow")
    suspend fun getDailyWeatherByCoordinates(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double
    ): WeatherDailyResponse
}
