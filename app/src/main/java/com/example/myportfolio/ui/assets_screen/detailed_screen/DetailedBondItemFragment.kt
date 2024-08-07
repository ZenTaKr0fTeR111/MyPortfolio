package com.example.myportfolio.ui.assets_screen.detailed_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myportfolio.DateTimeUtils
import com.example.myportfolio.R
import com.example.myportfolio.databinding.FragmentDetailedBondItemBinding
import com.example.myportfolio.domain.models.Bond
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedBondItemFragment : Fragment() {
    private var _binding: FragmentDetailedBondItemBinding? = null
    val binding
        get() = _binding!!
    private val args: DetailedBondItemFragmentArgs by navArgs()
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
        _binding = FragmentDetailedBondItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchAsset(args.assetId)
        viewModel.asset.observe(viewLifecycleOwner) { newBond ->
            setupInfo(newBond as Bond)
        }
    }

    private fun setupInfo(bond: Bond) {
        binding.apply {
            nameText.text = bond.name
            codeNameText.text = bond.code
            parText.text = getString(R.string.value, bond.getBasePrice(), bond.baseCurrency.symbol)
            rateText.text = getString(R.string.interest_rate, bond.rate)
            issueDateValue.text = DateTimeUtils
                .formatBondDetails(bond.dateOfIssuance)
            maturityDateValue.text = DateTimeUtils
                .formatBondDetails(bond.dateOfIssuance.plusYears(bond.yearsTillMaturity))
        }
    }

    override fun onDestroyView() {
        _binding = null
        actionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}
