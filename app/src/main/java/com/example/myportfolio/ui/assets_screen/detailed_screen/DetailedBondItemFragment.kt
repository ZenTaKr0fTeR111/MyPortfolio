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
import com.example.myportfolio.databinding.FragmentDetailedBondItemBinding
import com.example.myportfolio.domain.models.Bond
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DetailedBondItemFragment : Fragment() {
    private var _binding: FragmentDetailedBondItemBinding? = null
    val binding
        get() = _binding!!
    private val args: DetailedBondItemFragmentArgs by navArgs()
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
            issueDateValue.text = bond.dateOfIssuance
                .format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
            maturityDateValue.text = bond.dateOfIssuance.plusYears(bond.yearsTillMaturity)
                .format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
