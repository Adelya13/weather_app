package ru.valisheva.weather_app.data.repositories

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.repositories.LocationRepository
import javax.inject.Inject

private const val DEFAULT_LATITUDE = 55.80
private const val DEFAULT_LONGITUDE = 49.10

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : LocationRepository {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var coordinates : CityCoordinates = CityCoordinates(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)


    @SuppressLint("MissingPermission")
    override fun getLocation(): CityCoordinates {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    coordinates = CityCoordinates(location.latitude,location.longitude)
                    println("COORDINATES")
                    println(coordinates.toString())
                }
            }
        return coordinates
    }
    override fun getDefaultLocation(): CityCoordinates {
        return coordinates
    }
}