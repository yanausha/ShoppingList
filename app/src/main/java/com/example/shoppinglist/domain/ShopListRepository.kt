package com.example.shoppinglist.domain

interface ShopListRepository {

    fun getShopList():  List<ShopItem>
    fun getItemId(itemShopId: Int): ShopItem
    fun addShopItem(itemShop: ShopItem)
    fun deleteShopItem(itemShop: ShopItem)
    fun editListItem(itemShop: ShopItem)
}