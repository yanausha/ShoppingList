package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getItemId(itemShopId: Int): ShopItem {
        return shopList.find {
            it.id == itemShopId
        } ?: throw RuntimeException("Element with id $itemShopId not found")
    }

    override fun addShopItem(itemShop: ShopItem) {
        if (itemShop.id == ShopItem.UNDEFINED_ID) itemShop.id = autoIncrementId++
        shopList.add(itemShop)
    }

    override fun deleteShopItem(itemShop: ShopItem) {
        shopList.remove(itemShop)
    }

    override fun editListItem(itemShop: ShopItem) {
        val oldElement = getItemId(itemShop.id)
        shopList.remove(oldElement)
        addShopItem(itemShop)
    }
}
