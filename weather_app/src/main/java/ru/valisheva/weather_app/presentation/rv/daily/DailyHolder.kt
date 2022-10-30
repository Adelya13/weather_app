package ru.valisheva.weather_app.presentation.rv.daily

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.valisheva.weather_app.databinding.ItemWeekWeatherBinding
import ru.valisheva.weather_app.domain.models.DailyWeather
import ru.valisheva.weather_app.presentation.utils.TempImgService

class DailyHolder(
private val binding: ItemWeekWeatherBinding
) : RecyclerView.ViewHolder(binding.root){

    @SuppressLint("SetTextI18n")
    fun bind(item: DailyWeather ) {
        with(binding) {
            val tempImgService: TempImgService = TempImgService((item.minTemp+item.maxTemp)/2)
            tvTempInterval.text = item.minTemp.toString() +"° : "+ item.maxTemp.toString() +"°"
            tvWeekday.text = item.weekday
            ivWeather.setImageResource(tempImgService.changeTemImg())
        }
    }

    companion object {
        fun create(
            parent: ViewGroup
        ) = DailyHolder(
            ItemWeekWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

