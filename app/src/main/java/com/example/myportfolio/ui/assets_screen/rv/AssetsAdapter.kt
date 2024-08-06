package com.example.myportfolio.ui.assets_screen.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myportfolio.databinding.AssetBondItemBinding
import com.example.myportfolio.databinding.AssetListItemBinding
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.ui.models.UIAsset
import com.example.myportfolio.ui.models.UIBond
import com.example.myportfolio.ui.models.UICurrency
import com.example.myportfolio.ui.models.UIStock

class AssetsAdapter(
    private val onItemClicked: (Asset) -> Unit
) : ListAdapter<UIAsset, AssetViewHolder<*>>(AssetsDiffCallback) {

    enum class AssetType {
        STOCK,
        CURRENCY,
        BOND,
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is UIStock -> AssetType.STOCK.ordinal
        is UIBond -> AssetType.BOND.ordinal
        is UICurrency -> AssetType.CURRENCY.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder<*> {
        return when (viewType) {
            AssetType.STOCK.ordinal -> StockViewHolder(
                AssetListItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent,
                    false
                )
            )

            AssetType.BOND.ordinal -> BondViewHolder(
                AssetBondItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent,
                    false
                )
            )

            AssetType.CURRENCY.ordinal -> CurrencyViewHolder(
                AssetListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("${javaClass.name}: Unknown asset type.")
        }
    }

    override fun onBindViewHolder(holder: AssetViewHolder<*>, position: Int) {
        when (val asset = getItem(position)) {
            is UIStock -> (holder as StockViewHolder).bind(asset, onItemClicked)
            is UIBond -> (holder as BondViewHolder).bind(asset, onItemClicked)
            is UICurrency -> (holder as CurrencyViewHolder).bind(asset, onItemClicked)
        }
    }

    object AssetsDiffCallback : DiffUtil.ItemCallback<UIAsset>() {
        override fun areItemsTheSame(oldItem: UIAsset, newItem: UIAsset): Boolean {
            return oldItem.domainAsset.id == newItem.domainAsset.id
        }

        override fun areContentsTheSame(oldItem: UIAsset, newItem: UIAsset): Boolean {
            return oldItem == newItem
        }
    }
}
