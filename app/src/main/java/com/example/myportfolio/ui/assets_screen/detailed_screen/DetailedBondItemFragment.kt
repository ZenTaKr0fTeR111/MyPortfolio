package com.example.myportfolio.ui.assets_screen.detailed_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myportfolio.FormatSubject
import com.example.myportfolio.R
import com.example.myportfolio.databinding.FragmentDetailedBondItemBinding
import com.example.myportfolio.formatAppDetails
import com.example.myportfolio.ui.MainViewModel
import com.example.myportfolio.ui.models.UIBond
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedBondItemFragment : Fragment() {
    private var _binding: FragmentDetailedBondItemBinding? = null
    val binding
        get() = _binding!!
    private val args: DetailedBondItemFragmentArgs by navArgs()
    private val viewModel: DetailedAssetViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()
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

        viewModel.fetchAsset(args.assetId, activityViewModel.defaultCurrency)
        viewModel.asset.observe(viewLifecycleOwner) { newBond ->
            setupInfo(newBond as UIBond)
        }
    }

    private fun setupInfo(bond: UIBond) {
        binding.apply {
            nameText.text = bond.domainAsset.name
            codeNameText.text = bond.domainAsset.code
            parText.text = getString(
                R.string.value,
                bond.getPriceString(requireContext())
            )
            rateText.text = getString(R.string.interest_rate, bond.domainAsset.rate)
            issueDateValue.text = bond.domainAsset.dateOfIssuance
                .formatAppDetails(FormatSubject.BOND_DETAILS)
            maturityDateValue.text = bond.domainAsset.dateOfIssuance.plusYears(
                bond.domainAsset.yearsTillMaturity
            ).formatAppDetails(FormatSubject.BOND_DETAILS)
        }
    }

    override fun onDestroyView() {
        _binding = null
        actionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}
