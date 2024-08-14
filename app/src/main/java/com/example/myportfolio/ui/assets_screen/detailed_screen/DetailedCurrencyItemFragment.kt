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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myportfolio.ChartDateFormatter
import com.example.myportfolio.R
import com.example.myportfolio.configureChart
import com.example.myportfolio.databinding.FragmentDetailedCurrencyItemBinding
import com.example.myportfolio.domain.interactors.ConversionInteractor.Period
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.ui.MainViewModel
import com.example.myportfolio.ui.models.UICurrency
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedCurrencyItemFragment : Fragment() {
    private var _binding: FragmentDetailedCurrencyItemBinding? = null
    val binding
        get() = _binding!!
    private val args: DetailedCurrencyItemFragmentArgs by navArgs()
    private val viewModel: DetailedAssetViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()
    private var actionBar: ActionBar? = null

    private val targetCurrencyPickerLister = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            viewModel.fetchConversionRates(
                CurrencyCode.entries[position]
            )
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    private val periodPickerListener = { group: ChipGroup, _: List<Int> ->
        val period = when (group.checkedChipId) {
            R.id.rates_month_period_chip -> Period.MONTH
            R.id.rates_year_period_chip -> Period.YEAR
            else -> Period.WEEK
        }
        viewModel.changePeriod(period)
    }

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

            conversionRateChart.configureChart(requireContext())

            targetCurrencyPicker.onItemSelectedListener = targetCurrencyPickerLister
            ratesPeriodChipGroup.setOnCheckedStateChangeListener(periodPickerListener)
        }
        viewModel.fetchAsset(args.assetId, activityViewModel.defaultCurrency)
        viewModel.asset.observe(viewLifecycleOwner) { newCurrency ->
            setupInfo(newCurrency as UICurrency)
        }
    }

    private fun setupInfo(currency: UICurrency) {
        binding.apply {
            nameText.text = currency.domainAsset.name
            codeNameText.text = currency.domainAsset.code.toString()
            sourceCurrencyPicker
                .setSelection(CurrencyCode.entries.indexOf(currency.domainAsset.code))

            val chipToCheck = when (viewModel.period) {
                Period.MONTH -> ratesMonthPeriodChip
                Period.YEAR -> ratesYearPeriodChip
                else -> ratesWeekPeriodChip
            }
            chipToCheck.isChecked = true

            conversionRateChart.apply {
                invalidate()
                data = LineData(LineDataSet(currency.conversionRates, "Currency Rates"))
                data.setValueTextColor(requireContext().getColor(R.color.app_text))
                xAxis.valueFormatter = ChartDateFormatter
                notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        actionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}
