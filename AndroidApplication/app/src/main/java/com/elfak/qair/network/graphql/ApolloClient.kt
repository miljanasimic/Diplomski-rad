package com.elfak.qair.network.graphql

import com.apollographql.apollo3.ApolloClient

object ApolloClient {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("http://192.168.100.14:3000/graphql")
        .build()
}

//./gradlew :app:downloadApolloSchema --endpoint='http://localhost:3000/graphql' --schema=app/src/main/graphql/com/elfak/qair/schema.graphqls