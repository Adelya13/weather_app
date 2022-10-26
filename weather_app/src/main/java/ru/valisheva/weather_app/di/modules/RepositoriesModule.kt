package ru.valisheva.weather_app.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.valisheva.weather_app.data.repositories.WeatherRepositoryImpl
import ru.valisheva.weather_app.domain.repositories.WeatherRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun weatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

}
