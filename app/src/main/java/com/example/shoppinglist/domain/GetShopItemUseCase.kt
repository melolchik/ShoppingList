package com.example.shoppinglist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val repository: ShopListRepository) {
    suspend fun getShopItem(id : Int) : ShopItem = repository.getShopItem(id)
}