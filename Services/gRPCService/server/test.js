const AirQualityIndex = require('../models/airQualityIndex');
const mongoose = require('mongoose');
async function test(){
    res=await AirQualityIndex.find()
    console.log(res)
}

const uri = "mongodb://mongodb:27017/life-quality?retryWrites=true&w=majority";

function main() {
    
    mongoose.connect(uri).then(async () => {
        console.log('Connection to database successfull.')
        res=await AirQualityIndex.find()
        console.log(res)
        
    }).catch(err => {
        console.log(err)
        console.log('An error occured while connecting to the database.')
    });

    
}

main();