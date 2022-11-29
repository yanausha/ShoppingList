package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var linearLayoutShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutShopList = findViewById(R.id.LinearLayout_shopList)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            showList(it)
        }
    }

    private fun showList(list: List<ShopItem>) {

        linearLayoutShopList.removeAllViews()

        for (shopItem in list) {
            val layoutId = if (shopItem.enabled) R.layout.item_shop_enabled
            else R.layout.item_shop_disabled
            val view = LayoutInflater.from(this)
                .inflate(layoutId, linearLayoutShopList, false)
            val textViewName = view.findViewById<TextView>(R.id.textView_name)
            val textViewWeight = view.findViewById<TextView>(R.id.textView_weight)
            val textViewCount = view.findViewById<TextView>(R.id.textView_count)
            textViewName.text = shopItem.name
            textViewWeight.text = shopItem.weight.toString()
            textViewCount.text = shopItem.count.toString()

            view.setOnLongClickListener {
                viewModel.changeEnableState(shopItem)
                true
            }
            linearLayoutShopList.addView(view)
        }
    }
}