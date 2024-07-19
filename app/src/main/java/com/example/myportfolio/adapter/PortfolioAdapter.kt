package com.example.myportfolio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myportfolio.databinding.ViewholerPortfolioBinding
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.viewHolder.PortfolioViewHolder

class PortfolioAdapter : RecyclerView.Adapter<PortfolioViewHolder>() {
    var onClick: (Asset) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        return PortfolioViewHolder(
            ViewholerPortfolioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.setData(differ.currentList[position], onClick)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<Asset>() {
        override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
}
