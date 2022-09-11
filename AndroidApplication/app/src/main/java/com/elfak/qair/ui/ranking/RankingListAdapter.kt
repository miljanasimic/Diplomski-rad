package com.elfak.qair.ui.ranking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elfak.qair.CountryListQuery
import com.elfak.qair.R

class RankingListAdapter(private val countries: List<CountryListQuery.GetCountry>
, private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<RankingListAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.country_name)
        val rank: TextView = view.findViewById(R.id.country_rank)
        val weather: TextView = view.findViewById(R.id.country_weather)
        val total: TextView = view.findViewById(R.id.country_total)
        val countryLayout: LinearLayout = view.findViewById(R.id.country_rank_list_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.country_rank_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.name.text = country.country
        holder.rank.text = country.rank.toString()
        holder.weather.text = country.climate.toString()
        holder.total.text = country.total.toString()

        holder.countryLayout.setOnClickListener {
            onClickListener.onCountryClick(country.country!!)
        }
    }

    override fun getItemCount() = countries.size

    class OnClickListener(val clickListener: (country: String) -> Unit) {
        fun onCountryClick(country: String) = clickListener(country)
    }
}