package com.elfak.qair.ui.ranking

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.ApolloClient

class RankingViewModel : ViewModel() {
     val apolloClient = ApolloClient.Builder()
        .serverUrl("http://localhost:3000/graphql")
        .build()
}