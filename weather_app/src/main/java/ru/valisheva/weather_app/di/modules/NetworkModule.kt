package ru.valisheva.weather_app.di.modules

import androidx.databinding.ktx.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.valisheva.weather_app.data.api.MeteoApi
import ru.valisheva.weather_app.data.api.OpenWeatherApi
import ru.valisheva.weather_app.di.qualifiers.ApiKeyInterceptor
import ru.valisheva.weather_app.di.qualifiers.LoggingInterceptor
import ru.valisheva.weather_app.di.qualifiers.OkhttpMeteoInterceptor
import ru.valisheva.weather_app.di.qualifiers.OkhttpOpenWeatherInterceptor


private const val METEO_BASE_URL = "https://api.open-meteo.com/v1/"
private const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

private const val API_KEY = "fde244775c5ca4fee06868ab465e85e3"
private const val QUERY_API_KEY = "appid"


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @ApiKeyInterceptor
    fun apiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor() : Interceptor{
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
    }
    @Provides
    @OkhttpOpenWeatherInterceptor
    fun okhttpOpenWeather(
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor,
        @LoggingInterceptor httpLoggingInterceptor: Interceptor,

        ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        httpLoggingInterceptor
                    )
                }
            }
            .build()

    @Provides
    @OkhttpMeteoInterceptor
    fun okhttpMeteo(
        @LoggingInterceptor httpLoggingInterceptor: Interceptor,
        ): OkHttpClient =
        OkHttpClient.Builder()
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        httpLoggingInterceptor
                    )
                }
            }
            .build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun apiMeteo(
        @OkhttpMeteoInterceptor okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): MeteoApi =
        Retrofit.Builder()
            .baseUrl(METEO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(MeteoApi::class.java)

    @Provides
    fun apiOpenWeather(
        @OkhttpOpenWeatherInterceptor okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): OpenWeatherApi =
        Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(OpenWeatherApi::class.java)

}
