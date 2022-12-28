package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun editListItem(itemShop: ShopItem) {
        shopListRepository.editListItem(itemShop)
    }
}