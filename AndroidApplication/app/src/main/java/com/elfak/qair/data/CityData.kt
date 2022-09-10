package com.elfak.qair.data

import com.squareup.moshi.JsonClass
import java.util.*

data class CityData(val city: String,
val state: String,
val country: String,
val location: LocationCity,
val current: CurrentCityData) {

}

data class LocationCity(val type: String, val coordinates: DoubleArray)

data class CurrentCityData(val pollution: Pollution, val weather: Weather)


data class Pollution(val timeStamp: Date,
                val aqius: Int,
                val mainus: String,
                val aqicn: Int,
                val maincn: String)

data class Weather(val timeStamp: Date,
                val tempCelsius: Int,
                val pressure: Int,
                val humidity: Int,
                val windSpeed: Float,
                val windDirection: Int,
                val iconCode: String)
