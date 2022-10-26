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
import ru.valisheva.weather_app.data.api.Api
import ru.valisheva.weather_app.di.qualifiers.ApiKeyInterceptor
import ru.valisheva.weather_app.di.qualifiers.LoggingInterceptor


private const val BASE_URL = "https://api.open-meteo.com/v1/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @LoggingInterceptor
    fun provideLoggingInterceptor() : Interceptor{
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
    }

    @Provides
    fun okhttp(
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
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun api(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(Api::class.java)

}
