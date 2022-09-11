const AirQualityIndex = require('../models/airQualityIndex');
const _ = require('lodash');

async function streamCountryIndices(call){
    console.log(`received request ${call.request}....`)
    const name = call.request.name
    try{
        const indices = await AirQualityIndex.find({country: name})
        _.each(indices, function(aqIndex){
            call.write({
                country: aqIndex.country,
                aqi_index: aqIndex.aqi_index,
                status: aqIndex.date,
                date: {
                    seconds: aqIndex.date.getTime(),
                    nanos: aqIndex.date.getTime()
                }
            })
        })
        call.end()
    } catch (ex) {
        console.log(ex)
        call.end()
    }    
}

module.exports = streamCountryIndices;