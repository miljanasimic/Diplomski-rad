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

const resolvers = {
    RootQuery: {
        getCountries: (root, args) => getCountries()
    }
}
module.exports = resolvers