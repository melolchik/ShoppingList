package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl : ShopListRepository {

    private val shopList = sortedSetOf<ShopItem>(object : Comparator<ShopItem>{
        override fun compare(o1: ShopItem, o2: ShopItem): Int {
            return o1.id.compareTo(o2.id)
        }

    })
    private val shopListLD = MutableLiveData<List<ShopItem>>()
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
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
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

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateList(){
        shopListLD.value = shopList.toList()
    }
}