package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBinding
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when (viewType) {
            ENABLED_ITEM -> R.layout.item_shop_enabled
            DISABLED_ITEM -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknow view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding


        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.textViewName.text = shopItem.name
                binding.textViewWeight.text = shopItem.weight.toString()
                binding.textViewCount.text = shopItem.count.toString()
            }
            is ItemShopEnabledBinding -> {
                binding.textViewName.text = shopItem.name
                binding.textViewWeight.text = shopItem.weight.toString()
                binding.textViewCount.text = shopItem.count.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) ENABLED_ITEM else DISABLED_ITEM
    }

    companion object {

        const val ENABLED_ITEM = 1
        const val DISABLED_ITEM = 0

        const val MAX_POOL_SIZE = 20
    }
}
