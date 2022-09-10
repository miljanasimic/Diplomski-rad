package com.elfak.qair.ui.statistics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elfak.qair.AirQualityIndexServiceGrpc
import com.elfak.qair.CountryAQIndexResponse
import com.elfak.qair.CountryRequest
import com.elfak.qair.data.response.CityCurrentDataApiResponse
import com.elfak.qair.network.grpc.GrpcService
import io.grpc.stub.StreamObserver
import java.lang.Exception
import java.util.concurrent.TimeUnit

class StatisticsViewModel : ViewModel(){

    private lateinit var  stub: AirQualityIndexServiceGrpc.AirQualityIndexServiceStub

    private val _countryIndices = MutableLiveData<MutableList<CountryAQIndexResponse>>()
    val countryIndices: LiveData<MutableList<CountryAQIndexResponse>> = _countryIndices

    init {
        _countryIndices.postValue(mutableListOf())
    }

    fun getCountryIndices(countryName: String){
        try{
            val channel = GrpcService().createManagedChannel()
            stub = AirQualityIndexServiceGrpc.newStub(channel)
            val request = CountryRequest.newBuilder().setName(countryName).build()
            stub.streamCountryIndices(request, object : StreamObserver<CountryAQIndexResponse> {
                override fun onNext(newIndex: CountryAQIndexResponse?) {
                    if(newIndex!=null){
                        if(_countryIndices.value==null)
                            _countryIndices.value = mutableListOf(newIndex)
                        _countryIndices.value?.add(newIndex)
                    }
                }
                override fun onError(t: Throwable?) {
                    Log.e("Error grpc method", t?.message.toString())
                }
                override fun onCompleted() {
                    _countryIndices.postValue(_countryIndices.value)
                }
            })
            //Log.e("response", reply.toString())
            //channel?.shutdown()?.awaitTermination(1, TimeUnit.SECONDS)
            //bla bla
        } catch (ex: Exception){
            ex.message
            ex.toString()
        }

    }
}