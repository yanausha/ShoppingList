package com.example.shoppinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun editListItem(itemShop: ShopItem) {
        shopListRepository.editListItem(itemShop)
    }
}