package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
     init {
         for (i in 0 until 10){
             val item = ShopItem("Name $i",i,true)
             addShopItem(item)
         }
     }

    private var autoIncrementalId = 0
    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementalId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
       val oldItem = getShopItem(shopItem.id)
        shopList.remove(oldItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id }
            ?: throw RuntimeException("Element with id $id not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}