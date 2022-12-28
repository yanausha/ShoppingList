package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun getItemId(itemShopId: Int): ShopItem {
        return shopListRepository.getItemId(itemShopId)
    }
}