package com.elfak.qair.ui.ranking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.elfak.qair.CountryQuery
import com.elfak.qair.databinding.FragmentCountryDialogBinding
import com.elfak.qair.network.graphql.ApolloClient


class CountryDialogFragment : DialogFragment() {
    private var _binding: FragmentCountryDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            val countryName = arguments?.getString("name")
            if(countryName!=null){
                try{
                    val response = ApolloClient.apolloClient.query(CountryQuery(countryName)).execute()
                    val country = response.data?.getCountry
                    if(country!=null){
                        binding.textViewCountryName.text = country.country
                        binding.textViewStability.text = country.stability.toString()
                        binding.textViewSafety.text = country.safety.toString()
                        binding.textViewClimate.text = country.climate.toString()
                        binding.textViewCosts.text = country.costs.toString()
                        binding.textViewRights.text = country.rights.toString()
                        binding.textViewHealth.text = country.health.toString()
                        binding.textViewPopularity.text = country.popularity.toString()
                        binding.textViewTotal.text = country.total.toString()
                    }

                }
                catch(e: ApolloException){
                    Log.d("CountryQuery", "Failure", e)

                }

            }

        }
    }

}