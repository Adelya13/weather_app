package ru.valisheva.weather_app.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.valisheva.weather_app.domain.models.CityCoordinates
import ru.valisheva.weather_app.domain.repositories.LocationRepository
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : LocationRepository {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private lateinit var coordinates : CityCoordinates
    private val geo : Geocoder = Geocoder(context, Locale.ENGLISH)

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): CityCoordinates = suspendCoroutine{
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    coordinates = CityCoordinates(
                        ((location.latitude) * 100).toInt()/100.0,
                        (location.longitude * 100).toInt()/100.0
                    )
                    it.resume(coordinates)
                }else{
                    it.resumeWithException(Exception(" Location not found"))
                }
            }
    }

//  this method dont used
    override fun getCityByCoordinates(coordinates : CityCoordinates): String{
        val fullName = geo.getFromLocation(
            coordinates.latitude,
            coordinates.longitude,
            1
        )[0].subAdminArea
        val regex = """\s([\S]*)""".toRegex()

        return(regex.find(fullName)?.value.toString())
    }
}
