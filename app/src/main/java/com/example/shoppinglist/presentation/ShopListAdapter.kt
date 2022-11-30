package com.example.shoppinglist.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    val list = listOf<ShopItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val textViewName = view.findViewById<TextView>(R.id.textView_name)
        val textViewWeight = view.findViewById<TextView>(R.id.textView_weight)
        val textViewCount = view.findViewById<TextView>(R.id.textView_count)
    }
}