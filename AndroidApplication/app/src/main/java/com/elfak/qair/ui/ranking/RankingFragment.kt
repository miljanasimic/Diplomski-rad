package com.elfak.qair.ui.ranking

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.elfak.qair.CountryListQuery
import com.elfak.qair.R
import com.elfak.qair.databinding.FragmentRankingBinding
import com.elfak.qair.network.graphql.ApolloClient
import com.google.android.material.snackbar.Snackbar


class RankingFragment : Fragment() {

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            AlertDialog.Builder(requireContext(), R.style.alertDialog)
                .setTitle("Poređenje kvaliteta života")
                .setMessage("""Ova tabela upoređuje kvalitet života u 137 država. 
                    |
                    |Indeks poređenja se sastoji od 7 delova koji imaju važnu ulogu za trajno postojanje svake od zemalja.Za izračunavanje ukupnog indeksa uključeno je 36 faktora, koji su podeljeni u 7 glavnih oblasti - najbolja moguća vrednost u svakoj oblasti je 100.
                    |                    
                    |Za pregled svih 7 faktora kliknite na pojedinačnu državu.""".trimMargin())
                .show()
        }

        lifecycleScope.launchWhenResumed {
            binding.progressBar.visibility = View.VISIBLE
            binding.countryList.visibility = View.GONE
            val response = try {
                ApolloClient.apolloClient.query(CountryListQuery()).execute()
            } catch(e: ApolloException){
                Log.d("LaunchList", "Failure", e)
                null
            }
            val countries = response?.data?.getCountries
            if( countries!=null && !response.hasErrors()) {
                val adapter = RankingListAdapter(countries, RankingListAdapter.OnClickListener{ country -> openCountryDialog(country)})
                binding.countryList.adapter = adapter
                binding.countryList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }else{
                Toast.makeText(requireContext(), "Došlo je do greške prilikom učitavanja država, probajte malo kasnije.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCountryDialog(country: String) {
        val countryDialog = CountryDialogFragment()
        val bundle = Bundle()
        bundle.putString("name", country)
        countryDialog.arguments = bundle
        countryDialog.show(requireFragmentManager(), "countryDialog")
    }

}


