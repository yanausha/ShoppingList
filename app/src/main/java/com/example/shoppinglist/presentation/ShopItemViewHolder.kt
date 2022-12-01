package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textViewName = view.findViewById<TextView>(R.id.textView_name)
    val textViewWeight = view.findViewById<TextView>(R.id.textView_weight)
    val textViewCount = view.findViewById<TextView>(R.id.textView_count)
}
