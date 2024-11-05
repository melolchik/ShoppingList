package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl()
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemRepository = EditShopItemUseCase(repository)

    fun getShopItem(id : Int){
        val item = getShopItemUseCase.getShopItem(id)
    }

    fun addShopItem(inputName : String?, inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name,count)
        if(fieldsValid) {
            addShopItemUseCase.addShopItem(ShopItem(name,count,true))
        }
    }

    fun editShopItem(shopItem: ShopItem,inputName : String?, inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name,count)
        if(fieldsValid) {
            editShopItemRepository.editShopItem(ShopItem(name,count,true,shopItem.id))
        }
    }

    private fun parseName(name : String?) : String{
        return name?.trim() ?: ""
    }

    private fun parseCount(name : String?) : Int{
        return try {
            name?.toInt() ?: 0
        }catch (ex : Exception){
            0
        }
    }

    private fun validateInput(name : String, count : Int) : Boolean{
        var result = true;
        if(name.isBlank()){
            //ToDo: show error input name
            result = false
        }
        if(count <= 0){
            //ToDo: show error input count
            result = false
        }
        return result
    }
}