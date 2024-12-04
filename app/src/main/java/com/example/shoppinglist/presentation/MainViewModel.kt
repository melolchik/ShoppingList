package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase : GetShopListUseCase,
            private val deleteShopItemUseCase :DeleteShopItemUseCase,
            private val getShopItemUseCase :GetShopItemUseCase,
            private val editShopItemUseCase :EditShopItemUseCase,
    ) : ViewModel(){

    val shopList
        get() = getShopListUseCase.getShopList()

    fun deleteShopItem(item : ShopItem){
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(item)
        }

    }

    fun changeEnableState(shopItem : ShopItem){
        viewModelScope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}