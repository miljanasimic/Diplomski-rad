const {ApolloServer} = require('apollo-server-express')
const {makeExecutableSchema} = require('@graphql-tools/schema')
const resolvers = require('./resolvers')
const schema = require('./schema')

const executableSchema = makeExecutableSchema({
    typeDefs: schema, 
    resolvers: resolvers
})

const server = new ApolloServer({
    schema: executableSchema, 
    context: ({req})=>req,
})

module.exports = server