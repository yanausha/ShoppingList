package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_enabled,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        val status = if (shopItem.enabled) "Active" else "Not Active"
        if (shopItem.enabled) {
            holder.textViewName.text = "${shopItem.name} $status"
            holder.textViewWeight.text = shopItem.weight.toString()
            holder.textViewCount.text = shopItem.count.toString()
        }
        holder.view.setOnLongClickListener {
            TODO()
            true
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.textViewName.text = ""
        holder.textViewWeight.text = ""
        holder.textViewCount.text = ""
    }

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = view.findViewById<TextView>(R.id.textView_name)
        val textViewWeight = view.findViewById<TextView>(R.id.textView_weight)
        val textViewCount = view.findViewById<TextView>(R.id.textView_count)
    }
}