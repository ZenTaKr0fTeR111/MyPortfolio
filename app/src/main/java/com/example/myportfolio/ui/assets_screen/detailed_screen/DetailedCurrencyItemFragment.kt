package com.example.myportfolio.ui.assets_screen.detailed_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myportfolio.databinding.FragmentDetailedCurrencyItemBinding
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.CurrencyCode
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedCurrencyItemFragment : Fragment() {
    private var _binding: FragmentDetailedCurrencyItemBinding? = null
    val binding
        get() = _binding!!
    private val args: DetailedCurrencyItemFragmentArgs by navArgs()
    private val viewModel: DetailedAssetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        activity.onBackPressedDispatcher.addCallback(this) {
            actionBar?.setDisplayHomeAsUpEnabled(false)
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedCurrencyItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchAsset(args.assetId)
        viewModel.asset.observe(viewLifecycleOwner) { newCurrency ->
            setupInfo(newCurrency as Currency)
        }
    }

    private fun setupInfo(currency: Currency) {
        binding.apply {
            nameText.text = currency.name
            codeNameText.text = currency.code.toString()

            setupCurrencyPicker(currency)

            conversionRateChart.apply {
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                legend.isEnabled = false
            }
            viewModel.conversionRates.observe(viewLifecycleOwner) { newConversionRates ->
                conversionRateChart.apply {
                    invalidate()
                    data = LineData(LineDataSet(newConversionRates, "Currency Rates"))
                    xAxis.valueFormatter = DateFormatter
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun setupCurrencyPicker(currency: Currency) {
        binding.apply {
            sourceCurrencyPicker.setSelection(CurrencyCode.entries.indexOf(currency.code))
            sourceCurrencyPicker.isEnabled = false

            targetCurrencyPicker.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.fetchConversionRates(
                            currency.code,
                            CurrencyCode.entries[position]
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
