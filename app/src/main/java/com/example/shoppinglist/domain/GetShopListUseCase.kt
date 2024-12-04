package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(private val repository: ShopListRepository) {
    fun getShopList() : LiveData<List<ShopItem>>
    = repository.getShopList()
}