package com.elfak.qair.ui.statistics

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elfak.qair.R
import com.elfak.qair.databinding.FragmentStatisticsBinding
import com.elfak.qair.ui.helpers.ChartCustomFormatter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewCurrentCountry.visibility = View.GONE
        binding.chart.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        binding.buttonSearchCountry.setOnClickListener { view ->
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.search_country_statistic_dialog, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.editTextEnterCountry)
            AlertDialog.Builder(requireContext())
                        .setTitle("Pretraživanje države za statistiku o kvalitetu vazduha")
                        .setView(dialogLayout)
                        .setPositiveButton("Pretraži") { _, _ ->
                            binding.progressBar.visibility = View.VISIBLE
                            statisticsViewModel.getCountryIndices(editText.text.toString())
                        }
                        .show()
        }

        statisticsViewModel.country.observe(viewLifecycleOwner) { country ->
            binding.textViewCurrentCountry.text = "Podaci za državu: ${country}"
            binding.textViewCurrentCountry.visibility = View.VISIBLE
        }

        statisticsViewModel.countryIndices.observe(viewLifecycleOwner) { indices ->
            if(indices.size!=0){
                val entries: MutableList<Entry> = ArrayList()
                indices.forEach {
                    entries.add(Entry(it.date.seconds.toFloat(), it.aqiIndex.toFloat()))
                }

                val dataSet = LineDataSet(entries, "Indeks kvaliteta vazduha meren svakog dana u prethodnih dva meseca")
                dataSet.color = Color.rgb(61, 90, 128)
                dataSet.setCircleColor(Color.rgb(152, 193, 217));
                dataSet.highLightColor = Color.rgb(226, 149, 120);
                dataSet.lineWidth = 2f
                val lineData = LineData(dataSet)
                binding.chart.xAxis.valueFormatter = ChartCustomFormatter()
                binding.chart.data = lineData
                binding.chart.description.isEnabled = false
                binding.chart.invalidate()
                binding.chart.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } else{
                binding.textViewCurrentCountry.visibility = View.GONE
                binding.chart.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Aplikacija nema podatke za ovu državu, probajte malo kasnije.", Toast.LENGTH_SHORT).show()
            }
        }

        statisticsViewModel.getCountryIndices("Serbia")
    }

}