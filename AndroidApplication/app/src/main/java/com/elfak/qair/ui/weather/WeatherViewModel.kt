package com.elfak.qair.ui.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elfak.qair.data.response.CitiesInStateCountyApiResponse
import com.elfak.qair.data.response.CityCurrentDataApiResponse
import com.elfak.qair.data.response.StatesInCountryApiResponse
import com.elfak.qair.data.response.SupportedCountriesApiResponse
import com.elfak.qair.network.rest.IQAirApi
import kotlinx.coroutines.launch
import java.util.Collections.emptyList

class WeatherViewModel : ViewModel(){

    private val _supportedCountries = MutableLiveData<SupportedCountriesApiResponse>()
    val supportedCountries: LiveData<SupportedCountriesApiResponse> = _supportedCountries

    private val _statesInCountry = MutableLiveData<StatesInCountryApiResponse>()
    val statesInCountry: LiveData<StatesInCountryApiResponse> = _statesInCountry

    private val _citiesInStateCountry = MutableLiveData<CitiesInStateCountyApiResponse>()
    val citiesInStateCountry: LiveData<CitiesInStateCountyApiResponse> = _citiesInStateCountry

    private val _cityCurrentData = MutableLiveData<CityCurrentDataApiResponse>()
    val cityCurrentData: LiveData<CityCurrentDataApiResponse> = _cityCurrentData

    private val _country: MutableLiveData<String?> = MutableLiveData<String?>()
    val country: LiveData<String?> = _country

    private val _state: MutableLiveData<String?> = MutableLiveData<String?>()
    val state: LiveData<String?> = _state

    private val _city: MutableLiveData<String?> = MutableLiveData<String?>()
    val city: LiveData<String?> = _city


    fun getSupportedCountries() {
        viewModelScope.launch {
            try{
                val result = IQAirApi.retrofitService.getSupportedCountries()
                _supportedCountries.value = result
            } catch (ex: Exception){
                _supportedCountries.value = SupportedCountriesApiResponse("failed", emptyList())
            }

        }
    }
    fun getStatesInCountry() {
        viewModelScope.launch {
            try {
                val result = IQAirApi.retrofitService.getStatesInCountry(_country.value!!)
                _statesInCountry.value = result
            } catch (ex: Exception){
                _statesInCountry.value = StatesInCountryApiResponse("failed", emptyList())
            }
        }
    }

    fun getCitiesInStateCountry() {
        viewModelScope.launch {
            try {
                val result = IQAirApi.retrofitService.getCitiesInStateCountry(_country.value!!, _state.value!!)
                _citiesInStateCountry.value = result
            } catch (ex: Exception){
                Log.wtf(".", ex)
                _citiesInStateCountry.value = CitiesInStateCountyApiResponse(ex.message!!, emptyList())
            }
        }
    }

    fun getCityData() {
        viewModelScope.launch {
            try{
                val result = IQAirApi.retrofitService.getCityCurrentData(_city.value!!, _state.value!!, _country.value!!)
                _cityCurrentData.value = result
            } catch (ex: Exception){
                Log.wtf(".", ex)
                _cityCurrentData.value = CityCurrentDataApiResponse(ex.message!!, null)
            }
        }
    }

    fun getNearestCityData(currentLocation: Location) {
        viewModelScope.launch {
            try{
                val result = IQAirApi.retrofitService.getNearestCityCurrentData(currentLocation.latitude, currentLocation.longitude)
                _country.value = result.data?.country
                _state.value = result.data?.state
                _city.value = result.data?.city
                _cityCurrentData.value = result
            } catch (ex: Exception){
                Log.wtf(".", ex)
                _cityCurrentData.value = CityCurrentDataApiResponse(ex.message!!, null)
            }
        }
    }

    fun switchCountry(name: String){
        _country.value = name
    }

    fun switchState(name: String){
        _state.value = name
    }

    fun switchCity(name: String){
        _city.value = name
    }
}