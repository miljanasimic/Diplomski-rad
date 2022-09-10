package com.elfak.qair.network.grpc

import com.elfak.qair.AirQualityIndexServiceGrpc
import com.elfak.qair.CountryAQIndexResponse
import com.elfak.qair.CountryRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import java.io.Closeable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/*class AQIndexClient() : Closeable {
    private val channel = ManagedChannelBuilder
        .forAddress("http://192.168.100.14", 50051)
        .usePlaintext()
        //.keepAliveTime(30, TimeUnit.SECONDS)
        //.keepAliveWithoutCalls(true)
        .build()
    private val stub = AirQualityIndexServiceGrpc.newBlockingStub(channel)
    private val stub = AirQualityIndexServiceGrpc.newStub(channel)
    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
    suspend fun getCountryIndices(countryName: String){
        val request = CountryRequest.newBuilder()
            .setName(countryName)
            .build()

        val response = stub.streamCountryIndices(request).asFlow().collect {
            it.aqiIndex
            it.country
        }
    }
}

suspend fun main() {

    //val clientStub = AirQualityIndexServiceGrpc.AirQualityIndexServiceStub(channel)
}*/

class GrpcService(){
    fun createManagedChannel() = ManagedChannelBuilder
        .forAddress("192.168.100.14", 50051)
        .executor(Executors.newSingleThreadExecutor())
        .usePlaintext()
        .build()
}