const PROTO_PATH = __dirname + '/service.proto';
const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');

const packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    })

const service = grpc.loadPackageDefinition(packageDefinition).airqualityindexservice;

function main() {
    const client = new service.AirQualityIndexService('localhost:50051', grpc.credentials.createInsecure())
    const call = client.streamCountryIndices({name: 'China'})
    console.log('Client calling server`s method')
    call.on('data', function(response){
        console.log(response)
    })
    call.on('end', function(){
        console.log('All indices received!')
    })
}

main();