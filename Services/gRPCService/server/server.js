const PROTO_PATH = __dirname + '/service.proto';
const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const mongoose = require('mongoose');

const streamCountryIndices = require('./methods');
const AirQualityIndex = require('../models/airQualityIndex');
const { insertData } = require('../helpers/dataset');


const packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    })

const service = grpc.loadPackageDefinition(packageDefinition).airqualityindexservice;

async function fillDatabase(){
    const dataCount = await AirQualityIndex.countDocuments();
    if(dataCount == 0){
        console.log('Indices collection is empty...')
        insertData('./data/air-quality-index.csv', AirQualityIndex)
    }
}
const uri = "mongodb://mongodb:27017/life-quality?retryWrites=true&w=majority";

function getServer() {
    var server = new grpc.Server();
    server.addService(service.AirQualityIndexService.service, {
        streamCountryIndices : streamCountryIndices
    });
    return server;
}

function main() {
    
    mongoose.connect(uri).then(() => {
        console.log('Connection to database successfull.')
        fillDatabase().then(() => {
            console.log('Database filled successfully.')
            const routeServer = getServer()
            routeServer.bindAsync('0.0.0.0:50051', grpc.ServerCredentials.createInsecure(), () => {
            routeServer.start();
            })
        }).catch(err => {
            console.log(err)
            console.log('Error occured while populating the database...')
        });
    }).catch(err => {
        console.log(err)
        console.log('An error occured while connecting to the database.')
    });

    
}

main();