const mongoose = require("mongoose");

const countryQualitySchema = new mongoose.Schema({
    rank: Number,
    country: String,
    stability: Number,
    rights: Number,
    health: Number,
    safety: Number,
    climate: Number,
    costs: Number,
    popularity: Number,
    total: Number
})
  
  const CountryQuality  =mongoose.model('country', countryQualitySchema);
  module.exports = CountryQuality;