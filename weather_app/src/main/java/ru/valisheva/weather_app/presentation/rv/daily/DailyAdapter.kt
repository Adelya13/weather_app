package ru.valisheva.weather_app.presentation.rv.daily

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.valisheva.weather_app.domain.models.DailyWeather

class DailyAdapter(
    private val list: ArrayList<DailyWeather>
) : RecyclerView.Adapter<DailyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder {
        return DailyHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DailyHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}