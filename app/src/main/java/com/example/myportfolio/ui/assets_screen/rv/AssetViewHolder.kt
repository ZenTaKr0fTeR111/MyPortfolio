package com.example.myportfolio.ui.assets_screen.rv

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myportfolio.R
import com.example.myportfolio.databinding.AssetBondItemBinding
import com.example.myportfolio.databinding.AssetListItemBinding
import com.example.myportfolio.ui.models.UIAsset
import com.example.myportfolio.ui.models.UIBond
import com.example.myportfolio.ui.models.UICurrency
import com.example.myportfolio.ui.models.UIStock

abstract class AssetViewHolder<T : UIAsset>(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(asset: T, onItemClicked: (Int) -> Unit)
}

class StockViewHolder(
    private val binding: AssetListItemBinding
) : AssetViewHolder<UIStock>(binding) {

    override fun bind(
        asset: UIStock,
        onItemClicked: (Int) -> Unit
    ) {
        binding.apply {
            nameText.text = asset.domainAsset.name
            codeNameText.text = asset.domainAsset.ticker
            priceText.text = asset.getPriceString(binding.root.context)
        }
        itemView.setOnClickListener { onItemClicked(asset.domainAsset.id) }
    }
}

class BondViewHolder(
    private val binding: AssetBondItemBinding
) : AssetViewHolder<UIBond>(binding) {

    override fun bind(
        asset: UIBond,
        onItemClicked: (Int) -> Unit
    ) {
        binding.apply {
            nameText.text = asset.domainAsset.name
            codeNameText.text = asset.domainAsset.code
            rateText.text = binding.root.context.getString(R.string.rate, asset.domainAsset.rate)
            parText.text = asset.getPriceString(binding.root.context)
        }
        itemView.setOnClickListener { onItemClicked(asset.domainAsset.id) }
    }
}

class CurrencyViewHolder(
    private val binding: AssetListItemBinding
) : AssetViewHolder<UICurrency>(binding) {

    override fun bind(
        asset: UICurrency,
        onItemClicked: (Int) -> Unit
    ) {
        binding.apply {
            nameText.text = asset.domainAsset.name
            codeNameText.text = asset.domainAsset.code.toString()
            priceText.text = asset.getPriceString(binding.root.context)
        }
        itemView.setOnClickListener { onItemClicked(asset.domainAsset.id) }
    }
}
