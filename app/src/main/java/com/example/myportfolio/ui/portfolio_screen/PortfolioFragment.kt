package com.example.myportfolio.ui.portfolio_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myportfolio.adapter.PortfolioAdapter
import com.example.myportfolio.data.datasource.assetsStorage
import com.example.myportfolio.databinding.FragmentPortfolioBinding

class PortfolioFragment : Fragment() {
    private var _binding: FragmentPortfolioBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val portfolioAdapter = PortfolioAdapter()

        binding.apply {
            viewPortfolio.apply {
                adapter = portfolioAdapter
            }
        }
        binding.viewPortfolio.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        portfolioAdapter.differ.submitList(assetsStorage)

//             val adapter = portfolioAdapter { assetId ->
//            findNavController().navigate(
//                PortfolioFragment.actionPortfolioFragmenttoDetailedPortfolioFragment(
//                    assetId
//                )--помогите,пожалуйста ,что мне здесь надо исправить?????(мб AssetId)
//            )
//        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
