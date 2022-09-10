const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors')

const services = require('./services')
const CountryQuality = require('../models/countryQuality')
const { insertData } = require('../helpers/dataset');


async function startGqlServer(){
    const server = services['graphql']
    await server.start()
    server.applyMiddleware({ app })
}

async function fillDatabase(){
    const dataCount = await CountryQuality.countDocuments();
    if(dataCount == 0){
        console.log('Countries collection is empty... countries...')
        insertData('./data/quality-of-life.csv', CountryQuality)
    }
}

const uri = "mongodb://mongodb:27017/life-quality?retryWrites=true&w=majority";

mongoose.connect(uri).then(() => {
    console.log('Connection to database successfull.')
    fillDatabase().then(() => {
        console.log('Database filled successfully.')
    }).catch(err => {
        console.log(err)
        console.log('Error occured while populating the database...')
    });
}).catch(err => {
    console.log(err)
    console.log('An error occured while connecting to the database.')
});

const app = express()
app.use(cors())


startGqlServer()

module.exports = app