package com.example.myportfolio.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.myportfolio.databinding.ViewholerPortfolioBinding
import com.example.myportfolio.domain.models.Asset

class PortfolioViewHolder(
    private var binding: ViewholerPortfolioBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun setData(item: Asset, onClick: (Asset) -> Unit) {
        binding.apply {
            ViewholderPortfolio.setOnClickListener {
                onClick(item)
            }
//            imageView.setImageResource(item.imageRes)
            tvAssetName.text = item.name
        }
    }
}
