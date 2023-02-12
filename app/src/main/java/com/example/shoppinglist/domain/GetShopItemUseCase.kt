package com.example.shoppinglist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun getItemId(itemShopId: Int): ShopItem {
        return shopListRepository.getItemId(itemShopId)
    }
}