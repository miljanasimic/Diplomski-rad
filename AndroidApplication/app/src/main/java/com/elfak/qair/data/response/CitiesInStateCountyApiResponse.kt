package com.elfak.qair.data.response

import com.elfak.qair.data.City

data class CitiesInStateCountyApiResponse(val status: String,
                                          val cities: List<City>)
