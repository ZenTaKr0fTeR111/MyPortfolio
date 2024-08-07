package com.example.myportfolio.ui.assets_screen.detailed_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myportfolio.DateTimeUtils
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
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        actionBar = activity.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        activity.onBackPressedDispatcher.addCallback(this) {
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

        binding.apply {
            sourceCurrencyPicker.isEnabled = false

            conversionRateChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            conversionRateChart.legend.isEnabled = false
        }
        viewModel.fetchAsset(args.assetId)
        viewModel.asset.observe(viewLifecycleOwner) { newCurrency ->
            setupInfo(newCurrency as Currency)
        }
        viewModel.conversionRates.observe(viewLifecycleOwner) { newConversionRates ->
            binding.conversionRateChart.apply {
                invalidate()
                data = LineData(LineDataSet(newConversionRates, "Currency Rates"))
                xAxis.valueFormatter = DateTimeUtils
                notifyDataSetChanged()
            }
        }
    }

    private fun setupInfo(currency: Currency) {
        binding.apply {
            nameText.text = currency.name
            codeNameText.text = currency.code.toString()

            sourceCurrencyPicker.setSelection(CurrencyCode.entries.indexOf(currency.code))
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
        actionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}
