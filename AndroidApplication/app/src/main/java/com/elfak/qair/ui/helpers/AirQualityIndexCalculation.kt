package com.elfak.qair.ui.helpers

import com.elfak.qair.R
import com.elfak.qair.data.helper.CHIndexDescription
import com.elfak.qair.data.helper.MainPollutantDescription
import com.elfak.qair.data.helper.USIndexDescription

class AirQualityIndexCalculation {
    companion object {
        fun returnCHDescription(indexValue: Int) : CHIndexDescription{
            return when (indexValue) {
                in 0..50 ->  CHIndexDescription.Excellent
                in 51..100 ->  CHIndexDescription.Good
                in 101..150 ->  CHIndexDescription.LightlyPolluted
                in 151..200 -> CHIndexDescription.ModeratelyPolluted
                in 201..300 ->  CHIndexDescription.HeavilyPolluted
                else ->  CHIndexDescription.SeverelyPolluted
            }
        }
        fun returnUSDescription(indexValue: Int) : USIndexDescription{
            return when (indexValue) {
                in 0..50 ->  USIndexDescription.Good
                in 51..100 ->  USIndexDescription.Moderate
                in 101..150 ->  USIndexDescription.UnhealthySensitive
                in 151..200 -> USIndexDescription.Unhealthy
                in 201..300 ->  USIndexDescription.VeryUnhealthy
                else -> USIndexDescription.Hazardous
            }
        }
        fun returnColor(indexValue: Int) : Int {
            return when (indexValue) {
                in 0..50 ->  R.color.airValue1
                in 51..100 ->  R.color.airValue2
                in 101..150 ->  R.color.airValue3
                in 151..200 -> R.color.airValue4
                in 201..300 ->  R.color.airValue5
                else -> R.color.airValue6
            }
        }
        fun returnMainPollutant(value: String): MainPollutantDescription {
            return when (value) {
                "p2" ->  MainPollutantDescription.PM2
                "p1" ->  MainPollutantDescription.PM10
                "o3" ->  MainPollutantDescription.O3
                "n2" ->  MainPollutantDescription.NO2
                "s2" ->  MainPollutantDescription.SO2
                else  ->  MainPollutantDescription.CO

            }
        }
    }
}