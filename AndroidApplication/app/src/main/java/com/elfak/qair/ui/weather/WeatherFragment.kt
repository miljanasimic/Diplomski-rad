package com.elfak.qair.ui.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elfak.qair.data.City
import com.elfak.qair.data.Country
import com.elfak.qair.data.State
import com.elfak.qair.databinding.FragmentWeatherBinding
import com.elfak.qair.ui.helpers.AirQualityIndexCalculation
import com.elfak.qair.ui.helpers.LocationHelpers
import com.google.android.gms.location.*
import com.elfak.qair.R


class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by viewModels()
    private var selectedCountry: Int = 0
    private var selectedState: Int = 0
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.layoutSelectCity.visibility = View.GONE
        binding.layoutCurrentCityData.visibility = View.GONE

        binding.selectCityButton.setOnClickListener {
            binding.progressBarWeatherPage.visibility = View.VISIBLE
            weatherViewModel.getSupportedCountries()
        }

        binding.hideButton.setOnClickListener {
            binding.layoutSelectCity.visibility = View.GONE
        }

        binding.countriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.citiesSpinner.visibility = View.GONE
                binding.statesSpinner.visibility = View.GONE
                selectedState=0
                if(selectedCountry++==0)
                    return
                weatherViewModel.switchCountry(parent?.getItemAtPosition(position).toString())
                binding.progressBarWeatherPage.visibility = View.VISIBLE
            }
        }
        binding.countriesSpinner.setSelection(0,false)
        binding.statesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.citiesSpinner.visibility = View.GONE
                if(selectedState++==0)
                    return
                weatherViewModel.switchState(parent?.getItemAtPosition(position).toString())
                binding.progressBarWeatherPage.visibility = View.VISIBLE
            }
        }

        binding.citiesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                weatherViewModel.switchCity(parent?.getItemAtPosition(position).toString())
                binding.progressBarWeatherPage.visibility = View.VISIBLE
            }
        }

        weatherViewModel.country.observe(viewLifecycleOwner) {
            weatherViewModel.getStatesInCountry()
        }

        weatherViewModel.state.observe(viewLifecycleOwner) {
            weatherViewModel.getCitiesInStateCountry()
        }

        weatherViewModel.city.observe(viewLifecycleOwner) {
            weatherViewModel.getCityData()
        }

        weatherViewModel.supportedCountries.observe(viewLifecycleOwner){ result ->
            binding.progressBarWeatherPage.visibility = View.GONE
            if(result.status!="success"){
                binding.layoutSelectCity.visibility = View.GONE
                Toast.makeText(requireContext(), "Došlo je do greške prilikom učitavanja država, molimo Vas probajte malo kasnije.", Toast.LENGTH_SHORT).show()
                return@observe
            }
            binding.layoutSelectCity.visibility = View.VISIBLE
            populateCountriesSpinner(binding.countriesSpinner, result.countries)
        }
        weatherViewModel.statesInCountry.observe(viewLifecycleOwner) { result ->
            binding.progressBarWeatherPage.visibility = View.GONE
            if(result.status!="success"){
                binding.statesSpinner.visibility = View.GONE
                Toast.makeText(requireContext(), "Došlo je do greške prilikom učitavanja oblasti, molimo Vas probajte malo kasnije.", Toast.LENGTH_SHORT).show()
                return@observe
            }
            binding.statesSpinner.visibility = View.VISIBLE
            populateStatesSpinner(binding.statesSpinner, result.states)
        }
        weatherViewModel.citiesInStateCountry.observe(viewLifecycleOwner) { result ->
            binding.progressBarWeatherPage.visibility = View.GONE
            if(result.status!="success"){
                binding.citiesSpinner.visibility = View.GONE
                Toast.makeText(requireContext(), "Došlo je do greške prilikom učitavanja gradova, molimo Vas probajte malo kasnije.", Toast.LENGTH_SHORT).show()
                return@observe
            }
            binding.citiesSpinner.visibility = View.VISIBLE
            populateCitiesSpinner(binding.citiesSpinner, result.cities)
        }
        weatherViewModel.cityCurrentData.observe(viewLifecycleOwner) { result ->
            binding.progressBarWeatherPage.visibility = View.GONE
            if(result.status!="success" || result.data==null){
                Toast.makeText(requireContext(), "Došlo je do greške prilikom učitavanja trenutnih podataka, molimo Vas probajte malo kasnije.", Toast.LENGTH_SHORT).show()
                return@observe
            }
            val cityData = result.data
            binding.locationTextView.text = "${cityData.city}, ${cityData.state}, ${cityData.country}"

            binding.iconCodeImageView.setImageResource(resources.getIdentifier(cityData.current.weather.iconCode.replace('0','_'), "drawable", context?.packageName))
            binding.tempTextView.text = cityData.current.weather.tempCelsius.toString() + " \u2103"
            binding.humidityTextView.text = "Vlažnost vazduha: "+ cityData.current.weather.humidity.toString() + "%"
            binding.pressureTextView.text = "Pritisak vazduha: "+ cityData.current.weather.pressure.toString() + "hPa"
            binding.windSpeedTextView.text = "Brzina vetra: "+ cityData.current.weather.windSpeed + "m/s"

            val circleCh = binding.chinaColor.drawable as GradientDrawable
            val circleUs = binding.usaColor.drawable as GradientDrawable
            circleCh.setColor(ContextCompat.getColor(requireContext(), AirQualityIndexCalculation.returnColor(cityData.current.pollution.aqicn)))
            circleUs.setColor(ContextCompat.getColor(requireContext(), AirQualityIndexCalculation.returnColor(cityData.current.pollution.aqius)))
            binding.aqiUsaQuality.text = AirQualityIndexCalculation.returnUSDescription(cityData.current.pollution.aqius).desc
            binding.aqiChinaQuality.text = AirQualityIndexCalculation.returnCHDescription(cityData.current.pollution.aqicn).desc

            binding.mainCh.text = AirQualityIndexCalculation.returnMainPollutant(cityData.current.pollution.maincn).desc
            binding.mainUsa.text = AirQualityIndexCalculation.returnMainPollutant(cityData.current.pollution.mainus).desc
            binding.aqiUsaValue.text = "${cityData.current.pollution.aqius} \u00B5g/m\u00B3"
            binding.aqiChinaValue.text = "${cityData.current.pollution.aqicn} \u00B5g/m\u00B3"
            AirQualityIndexCalculation.returnCHDescription(cityData.current.pollution.aqicn)

            binding.layoutCurrentCityData.visibility = View.VISIBLE
        }

        binding.loadNearestCityButton.setOnClickListener { loadNearestCityData() }

    }


    private fun populateCountriesSpinner(
        countriesSpinner: Spinner, countries: List<Country>
    ) {
        val countriesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countries)
        countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countriesSpinner.adapter = countriesAdapter
        val index = countries.indexOfLast { it.name == "Serbia" }
        countriesSpinner.setSelection(index)

    }
    private fun populateStatesSpinner(
        statesSpinner: Spinner, states: List<State>
    ) {
        val statesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, states)
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statesSpinner.adapter = statesAdapter
        val index = states.indexOfLast { it.name == "Central Serbia" }
        statesSpinner.setSelection(index)
    }
    private fun populateCitiesSpinner(
        citiesSpinner: Spinner, cities: List<City>
    ) {
        val citiesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cities)
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citiesSpinner.adapter = citiesAdapter
        val index = cities.indexOfLast { it.name == "Belgrade" }
        citiesSpinner.setSelection(index)

    }

    private fun checkPermission(): Boolean {
        val resultFineLocation = ContextCompat.checkSelfPermission(requireActivity().applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCoarseLocation = ContextCompat.checkSelfPermission(requireActivity().applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
        return resultFineLocation == PackageManager.PERMISSION_GRANTED && resultCoarseLocation == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 2)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = (context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun loadNearestCityData() {
        when(this.isLocationEnabled()) {
            false -> {
                val locationRequest = LocationHelpers.buildLocationRequest()
                LocationHelpers.buildLocationSettings(locationRequest, requireActivity())
                Toast.makeText(requireContext(), "Molimo Vas uključite lokaciju.", Toast.LENGTH_SHORT).show()
            }
            true -> {
                if(!checkPermission()){
                    requestPermission()
                    return
                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        binding.progressBarWeatherPage.visibility = View.VISIBLE
                        weatherViewModel.getNearestCityData(location)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Došlo je do greške prilikom očitavanja lokacije, probajte ponovo.", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

}