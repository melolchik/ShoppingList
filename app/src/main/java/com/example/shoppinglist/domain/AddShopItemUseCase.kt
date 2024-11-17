package com.example.shoppinglist.domain

class AddShopItemUseCase (private val repository: ShopListRepository){
    suspend fun addShopItem(shopItem: ShopItem) {
        repository.addShopItem(shopItem)
    }
}