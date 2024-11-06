package com.example.shoppinglist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.util.Random

object ShopListRepositoryImpl : ShopListRepository {

    const val TAG = "ShopListRepositoryImpl"


    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private val _shopListLD = MutableLiveData<List<ShopItem>>()


     init {
         for (i in 0 until 10){
             val item = ShopItem("Name $i",i,Random().nextBoolean())
             addShopItem(item)
         }
     }

    private var autoIncrementalId = 0
    override fun addShopItem(shopItem: ShopItem) {
        Log.d(TAG,"addShopItem $shopItem")
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
        return _shopListLD
    }

    private fun updateList(){
        _shopListLD.value = shopList.toList()
    }
}