package com.elfak.qair.network.rest

import com.elfak.qair.data.helper.CustomDateAdapter
import com.elfak.qair.data.response.CitiesInStateCountyApiResponse
import com.elfak.qair.data.response.CityCurrentDataApiResponse
import com.elfak.qair.data.response.StatesInCountryApiResponse
import com.elfak.qair.data.response.SupportedCountriesApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://192.168.100.14:5000"
private val moshi = Moshi.Builder()
    .add(CustomDateAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface IQAirApiService {
    @GET("SupportedDestinations/Countries")
    suspend fun getSupportedCountries(): SupportedCountriesApiResponse

    @GET("SupportedDestinations/States/{countryName}")
    suspend fun getStatesInCountry(@Path("countryName") countryName: String): StatesInCountryApiResponse

    @GET("SupportedDestinations/Cities/{countryName}/{stateName}")
    suspend fun getCitiesInStateCountry(@Path("countryName") countryName: String,
                                        @Path("stateName") stateName: String): CitiesInStateCountyApiResponse

    @GET("CityCurrentData/{cityName}/{stateName}/{countryName}")
    suspend fun getCityCurrentData(@Path("cityName") cityName: String,
                                   @Path("stateName") stateName: String,
                                   @Path("countryName") countryName: String): CityCurrentDataApiResponse

    @GET("CityCurrentData/{latitude}/{longitude}")
    suspend fun getNearestCityCurrentData(@Path("latitude") latitude: Double,
                                   @Path("stateName") stateName: Double): CityCurrentDataApiResponse
}

object IQAirApi {
    val retrofitService : IQAirApiService by lazy {
        retrofit.create(IQAirApiService::class.java)
    }
}