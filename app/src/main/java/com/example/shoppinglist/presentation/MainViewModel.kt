package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel(){
    //now it's clean architecture error
    //link to data layer - not right
    private val repository = ShopListRepositoryImpl()
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = MutableLiveData<List<ShopItem>>()

//    fun getShopList() : List<ShopItem> {
//        return getShopListUseCase.getShopList()
//    }

    fun getShopList() {
        val list = getShopListUseCase.getShopList()
        //shopList.value - only from main thread
        //shopList.postValue() - from any thread
        shopList.value = list
    }

    fun deleteShopItem(item : ShopItem){
        deleteShopItemUseCase.deleteShopItem(item)
        getShopList()
    }

    fun changeEnableState(shopItem : ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
        getShopList()
    }
}