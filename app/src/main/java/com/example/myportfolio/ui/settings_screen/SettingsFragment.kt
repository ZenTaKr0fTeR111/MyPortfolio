package com.example.myportfolio.ui.settings_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myportfolio.R
import com.example.myportfolio.databinding.FragmentSettingsBinding
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.currencies
import com.example.myportfolio.ui.MainViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    val binding
        get() = _binding!!

    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsNightModeSwitch.apply {
            isChecked = activityViewModel.isDarkMode.value ?: false
            setOnClickListener {
                activityViewModel.toggleDarkMode()
            }
        }
        setupCurrencyChoiceDialog()
        binding.settingsCurrencyValue.text = activityViewModel.defaultCurrency.value?.code?.name
    }

    private fun setupCurrencyChoiceDialog() {
        binding.settingsCurrency.setOnClickListener {
            val options = CurrencyCode.getStringValues().toTypedArray()
            val selectedOption = activityViewModel.defaultCurrency.value?.code?.ordinal ?: 0
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.currency_picker_title)
                .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .setSingleChoiceItems(options, selectedOption) { dialog, selectedIndex ->
                    val currency = currencies[CurrencyCode.entries[selectedIndex]]
                    currency?.let {
                        activityViewModel.saveDefaultCurrency(currency)
                        binding.settingsCurrencyValue.text = currency.code.name
                    }
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
