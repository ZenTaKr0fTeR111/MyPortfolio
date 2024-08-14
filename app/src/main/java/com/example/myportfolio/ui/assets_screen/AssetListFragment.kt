package com.example.myportfolio.ui.assets_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myportfolio.databinding.FragmentAssetListBinding
import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.Stock
import com.example.myportfolio.ui.MainViewModel
import com.example.myportfolio.ui.assets_screen.rv.AssetsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssetListFragment : Fragment() {
    private var _binding: FragmentAssetListBinding? = null
    val binding
        get() = _binding!!
    private val viewModel: AssetListViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AssetsAdapter { asset ->
            when (asset) {
                is Currency -> findNavController().navigate(
                    AssetListFragmentDirections
                        .actionAssetListFragmentToDetailedCurrencyItemFragment(asset.id)
                )
                is Stock -> findNavController().navigate(
                    AssetListFragmentDirections
                        .actionAssetListFragmentToDetailedStockItemFragment(asset.id)
                )
                is Bond -> findNavController().navigate(
                    AssetListFragmentDirections
                        .actionAssetListFragmentToDetailedBondItemFragment(asset.id)
                )
            }
        }
        binding.assetList.adapter = adapter
        binding.assetList.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        viewModel.initAssets(activityViewModel.defaultCurrency)
        viewModel.assets.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
