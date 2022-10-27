package ru.valisheva.weather_app.data.api.mappers

import ru.valisheva.weather_app.data.responses.CurrentWeatherResponse
import ru.valisheva.weather_app.data.responses.WeatherDailyResponse
import ru.valisheva.weather_app.data.responses.WeatherResponse
import ru.valisheva.weather_app.data.responses.WeatherShortResponse
import ru.valisheva.weather_app.domain.models.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WeatherMapper {

    fun mapCurrentWeather(response: CurrentWeatherResponse) : CurrentWeather = CurrentWeather(
        city = response.city,
        descriptions = response.descriptions[0].description
    )

    fun mapHourlyWeather(response: WeatherResponse) : CurrWeather = CurrWeather (
        currTime = response.currentWeather.time,
        currTemp = response.currentWeather.temp.toInt(),
    )

    fun mapDailyWeather(response: WeatherDailyResponse) : ArrayList<DailyWeather>{
        return mapToDaily(
            response.daily.time,
            response.daily.tempMin,
            response.daily.tempMax
        )
    }
    fun mapCityCoordinates(response: WeatherShortResponse) : CityCoordinates = CityCoordinates(
        latitude = response.coordinates.latitude,
        longitude = response.coordinates.longitude
    )

    private fun mapToDaily(time: ArrayList<String>,
                           minTemp: ArrayList<Double>,
                           maxTemp: ArrayList<Double>
    ) : ArrayList<DailyWeather>{
        val result = arrayListOf<DailyWeather>()

        var i = 0;
        while (i< time.size) {
            result.add(DailyWeather(
                if(i == 0) "Today" else normalizeWeekday(time[i]),
                minTemp[i].toInt(), maxTemp[i].toInt()))
            i++
        }
        return result
    }

    private fun normalizeWeekday(time: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = simpleDateFormat.parse(time).day
        return convertToDayOfWeek(date)
    }

    private fun convertToDayOfWeek(day: Int): String{
        if (day == 0) return "Sunday"
        if (day == 1) return "Monday"
        if (day == 2) return "Tuesday"
        if (day == 3) return "Wednesday"
        if (day == 4) return "Thursday"
        if (day == 5) return "Friday"
        if (day == 6) return "Saturday"
        return ""
    }
}
