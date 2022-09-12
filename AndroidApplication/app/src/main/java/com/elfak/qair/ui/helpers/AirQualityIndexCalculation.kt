package com.elfak.qair.ui.helpers

import com.elfak.qair.data.helper.CHIndexDescription

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
    }
}