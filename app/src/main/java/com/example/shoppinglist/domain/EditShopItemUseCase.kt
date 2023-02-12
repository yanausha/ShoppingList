package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editListItem(itemShop: ShopItem) {
        shopListRepository.editListItem(itemShop)
    }
}