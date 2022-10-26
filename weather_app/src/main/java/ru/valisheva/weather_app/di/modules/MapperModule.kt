package ru.valisheva.weather_app.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.valisheva.weather_app.data.api.mappers.WeatherMapper

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Provides
    fun provideWeatherMapper(): WeatherMapper = WeatherMapper()
}

