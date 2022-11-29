package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>
    fun getItemId(itemShopId: Int): ShopItem
    fun addShopItem(itemShop: ShopItem)
    fun deleteShopItem(itemShop: ShopItem)
    fun editListItem(itemShop: ShopItem)
}