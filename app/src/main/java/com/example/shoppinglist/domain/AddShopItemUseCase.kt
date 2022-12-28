package com.example.shoppinglist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun addShopItem(itemShop: ShopItem) {
        shopListRepository.addShopItem(itemShop)
    }
}