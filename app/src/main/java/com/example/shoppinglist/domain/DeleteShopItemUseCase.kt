package com.example.shoppinglist.domain

import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(private val repository: ShopListRepository) {
    suspend fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}