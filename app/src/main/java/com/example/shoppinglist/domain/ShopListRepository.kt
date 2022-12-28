package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun getItemId(itemShopId: Int): ShopItem
    suspend fun addShopItem(itemShop: ShopItem)
    suspend fun deleteShopItem(itemShop: ShopItem)
    suspend fun editListItem(itemShop: ShopItem)
}