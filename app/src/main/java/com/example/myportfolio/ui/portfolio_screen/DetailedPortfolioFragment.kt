package com.example.myportfolio.ui.portfolio_screen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myportfolio.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailedPortfolioFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed_portfolio, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // Настройка параметров bottom sheet
        dialog.behavior.apply {
            // Установка высоты bottom sheet (можно задать высоту в пикселях или WRAP_CONTENT)
            // peekHeight = 500 // Пример установки высоты в пикселях
            // peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO // Автоматический расчет высоты
            // ...

            // Дополнительные настройки behavior'а
            // ...
        }

        return dialog
    }
}
