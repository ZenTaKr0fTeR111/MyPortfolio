package com.example.myportfolio.ui.assets_screen.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myportfolio.databinding.AssetBondItemBinding
import com.example.myportfolio.databinding.AssetListItemBinding
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Stock

class AssetsAdapter(
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<AssetViewHolder>() {
    private var assetList = listOf<Asset>()

    enum class AssetType {
        STOCK,
        CURRENCY,
        BOND,
    }

    fun submitItems(newList: List<Asset>) {
        val diff = DiffUtil.calculateDiff(AssetsDiffCallback(assetList, newList))
        assetList = newList
        diff.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int) = when (assetList[position]) {
        is Stock -> AssetType.STOCK.ordinal
        is Bond -> AssetType.BOND.ordinal
        else -> AssetType.CURRENCY.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        return when (viewType) {
            AssetType.STOCK.ordinal -> StockViewHolder(
                AssetListItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent, false
                )
            )

            AssetType.BOND.ordinal -> BondViewHolder(
                AssetBondItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent, false
                )
            )

            else -> CurrencyViewHolder(
                AssetListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = assetList.size

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.bind(assetList[position], onItemClicked)
    }
}
