const AirQualityIndex = require('../models/airQualityIndex');
const _ = require('lodash');

async function streamCountryIndices(call){
    console.log(`received request ${call.request}....`)
    const name = call.request.name
    try{
        const indices = await AirQualityIndex.find({country: name})
        //let date = new proto.google.protobuf.Timestamp()
        _.each(indices, function(aqIndex){
            //console.log(aqIndex.date)
            console.log("cap",aqIndex.date)
            call.write({
                country: "cao",
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