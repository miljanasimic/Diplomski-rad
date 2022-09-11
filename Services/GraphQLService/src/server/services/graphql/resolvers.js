const CountryQuality = require('../../../models/countryQuality')
async function getCountries() {
    try{
        const response = await CountryQuality.find() 
        return response
    } catch (ex) {
        console.log(ex)
        return []
    }    
}

async function getCountry(countryName) {
    try{
        const country = await CountryQuality.findOne({country: countryName})
        console.log(countryName, country)
        return country
    } catch (ex) {
        console.log(ex)
        return {}
    }    
}

const resolvers = {
    RootQuery: {
        getCountries: (root, args) => getCountries(),
        getCountry: (root, args) => getCountry(args.countryName)
    }
}
module.exports = resolvers