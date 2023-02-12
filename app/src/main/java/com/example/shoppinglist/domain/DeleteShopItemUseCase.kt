package com.example.shoppinglist.domain

import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun deleteShopItem(itemShop: ShopItem) {
        shopListRepository.deleteShopItem(itemShop)
    }
}