package ru.valisheva.weather_app.presentation.services

import ru.valisheva.weather_app.R

class TempImgService(
    private val temp : Int
) {
    fun changeTemImg(): Int {
        return when(temp){
            in -Int.MAX_VALUE..0 -> R.mipmap.ic_cold
            in 0..10 -> R.mipmap.ic_normal_cold
            in 10..20 -> R.mipmap.ic_warm
            in 20..Int.MAX_VALUE -> R.mipmap.ic_hot
            else -> R.mipmap.ic_hot
        }
    }
}