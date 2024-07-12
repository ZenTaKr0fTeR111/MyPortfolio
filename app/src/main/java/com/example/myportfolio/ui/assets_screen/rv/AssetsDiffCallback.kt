package com.example.myportfolio.ui.assets_screen.rv

import androidx.recyclerview.widget.DiffUtil
import com.example.myportfolio.domain.models.Asset

class AssetsDiffCallback(
    private val oldList: List<Asset>,
    private val newList: List<Asset>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
