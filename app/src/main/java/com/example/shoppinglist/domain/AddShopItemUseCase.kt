package com.example.shoppinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun addShopItem(itemShop: ShopItem) {
        shopListRepository.addShopItem(itemShop)
    }
}