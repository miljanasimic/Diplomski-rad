package com.elfak.qair.network.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.Executors

class GrpcService(){
    fun createManagedChannel(): ManagedChannel = ManagedChannelBuilder
        .forAddress("192.168.100.14", 50051)
        .executor(Executors.newSingleThreadExecutor())
        .usePlaintext()
        .build()
}