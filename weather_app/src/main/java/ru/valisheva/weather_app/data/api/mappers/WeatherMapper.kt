package ru.valisheva.weather_app.data.api.mappers

import android.annotation.SuppressLint
import ru.valisheva.weather_app.data.responses.WeatherDailyResponse
import ru.valisheva.weather_app.data.responses.WeatherHourlyResponse
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.domain.models.HourlyWeather
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WeatherMapper {

    fun mapHourlyWeather(response: WeatherHourlyResponse) : HourlyWeather = HourlyWeather (
        currTime = normalizeTime(response.currentWeather.time),
        currTemp = response.currentWeather.temp,
        time = normalizeTime(response.hourly.time),
        temp = response.hourly.temp,
    )

    fun mapDailyWeather(response: WeatherDailyResponse) : DailyWeather = DailyWeather(
        time = normalizeTime(response.daily.time),
        tempMax = response.daily.tempMax,
        tempMin = response.daily.tempMin,
    )

    @SuppressLint("SimpleDateFormat")
    private fun normalizeTime(time: String): String {
        val localTime = SimpleDateFormat("HH", Locale.getDefault())
        return localTime.parse(time).toString()
    }

    private fun normalizeTime(times: ArrayList<String>) : ArrayList<String>{
        times.map { time->
            normalizeTime(time)
        }
        return times
    }
}
