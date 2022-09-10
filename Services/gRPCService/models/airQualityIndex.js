const mongoose = require("mongoose");

const airQualityIndexSchema = new mongoose.Schema({
  date: Date,
  country: String,
  status: String,
  aqi_index: Number
})
  
const AirQualityIndex = mongoose.model('airQualityIndex', airQualityIndexSchema);
module.exports = AirQualityIndex;