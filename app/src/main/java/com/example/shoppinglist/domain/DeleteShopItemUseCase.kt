package com.example.shoppinglist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun deleteShopItem(itemShop: ShopItem) {
        shopListRepository.deleteShopItem(itemShop)
    }
}