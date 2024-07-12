package com.example.myportfolio.ui.assets_screen.rv

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myportfolio.data.datasource.baseCurrency
import com.example.myportfolio.databinding.AssetBondItemBinding
import com.example.myportfolio.databinding.AssetListItemBinding
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.Stock

abstract class AssetViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(asset: Asset, onItemClicked: (Int) -> Unit)
}

class StockViewHolder(private val binding: AssetListItemBinding) : AssetViewHolder(binding) {

    override fun bind(asset: Asset, onItemClicked: (Int) -> Unit) {
        with(asset as Stock) {
            binding.nameText.text = name
            binding.codeNameText.text = ticker
            binding.priceText.text = getPrice()
            itemView.setOnClickListener { onItemClicked(asset.id) }
        }
    }
}

class BondViewHolder(private val binding: AssetBondItemBinding) : AssetViewHolder(binding) {

    override fun bind(asset: Asset, onItemClicked: (Int) -> Unit) {
        with(asset as Bond) {
            binding.nameText.text = name
            binding.codeNameText.text = code
            binding.parText.text = getPrice()
            binding.rateText.text = "$rate%"
            itemView.setOnClickListener { onItemClicked(asset.id) }
        }
    }
}

class CurrencyViewHolder(private val binding: AssetListItemBinding) : AssetViewHolder(binding) {

    override fun bind(asset: Asset, onItemClicked: (Int) -> Unit) {
        with(asset as Currency) {
            binding.nameText.text = name
            binding.codeNameText.text = code.toString()
            // TODO("Conversion rate to default currency")
            binding.priceText.text = "1.00${baseCurrency.symbol}"
            itemView.setOnClickListener { onItemClicked(asset.id) }
        }
    }
}
