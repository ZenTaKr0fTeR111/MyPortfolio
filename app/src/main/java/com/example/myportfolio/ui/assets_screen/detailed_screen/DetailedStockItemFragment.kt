package com.example.myportfolio.ui.assets_screen.detailed_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myportfolio.R
import com.example.myportfolio.databinding.FragmentDetailedStockItemBinding
import com.example.myportfolio.domain.models.Stock
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedStockItemFragment : Fragment() {
    private var _binding: FragmentDetailedStockItemBinding? = null
    val binding
        get() = _binding!!
    private val args: DetailedStockItemFragmentArgs by navArgs()
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
        _binding = FragmentDetailedStockItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchAsset(args.assetId)
        viewModel.asset.observe(viewLifecycleOwner) { newStock ->
            setupInfo(newStock as Stock)
        }
    }

    private fun setupInfo(stock: Stock) {
        binding.apply {
            nameText.text = stock.name
            codeNameText.text = stock.ticker
            valueText.text =
                getString(R.string.value, stock.getBasePrice(), stock.baseCurrency.symbol)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
