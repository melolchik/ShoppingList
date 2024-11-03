package com.example.shoppinglist.domain

class GetShopItemUseCase(private val repository: ShopListRepository) {
    fun getShopItem(id : Int) : ShopItem = repository.getShopItem(id)
}