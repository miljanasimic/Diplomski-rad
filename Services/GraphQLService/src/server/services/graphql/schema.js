const typeDefinitions = `
  type Country {
    rank: Int
    country: String
    stability: Int
    rights: Int
    health: Int
    safety: Int
    climate: Int
    costs: Int
    popularity: Int
    total: Int
  }

  type RootQuery {
    getCountries: [Country!]!
  }

  schema {
    query: RootQuery
  }  
`


module.exports = typeDefinitions