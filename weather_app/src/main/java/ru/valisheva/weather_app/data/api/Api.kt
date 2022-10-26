package ru.valisheva.weather_app.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.valisheva.weather_app.data.responses.WeatherDailyResponse
import ru.valisheva.weather_app.data.responses.WeatherHourlyResponse

//forecast?latitude=52.52&longitude=13.41&daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FMoscow
//forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,weathercode
interface Api{

    @GET("forecast?current_weather=true&hourly=temperature_2m,weathercode")
    suspend fun getHourlyWeatherByCoordinates(
        @Query("latitude") latitude : String,
        @Query("longitude") longitude : String
    ): WeatherHourlyResponse

    @GET("forecast?daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FMoscow")
    suspend fun getDailyWeatherByCoordinates(
        @Query("latitude") latitude : String,
        @Query("longitude") longitude : String
    ): WeatherDailyResponse
}
