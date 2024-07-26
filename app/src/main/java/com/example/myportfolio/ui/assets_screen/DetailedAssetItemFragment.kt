package com.example.myportfolio.ui.assets_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myportfolio.databinding.FragmentDetailedAssetItemBinding

class DetailedAssetItemFragment : Fragment() {
    private var _binding: FragmentDetailedAssetItemBinding? = null
    val binding
        get() = _binding!!
    private val args: DetailedAssetItemFragmentArgs by navArgs()

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
        _binding = FragmentDetailedAssetItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.assetDetails.text = args.assetId.toString()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
