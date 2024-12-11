package com.example.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.icu.lang.UScript.COMMON
import android.net.Uri
import android.util.Log
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.presentation.COMMON_TAG
import com.example.shoppinglist.presentation.ShoppingListApp
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    private val component by lazy{
        (context as ShoppingListApp).component

    }

    @Inject
    lateinit var shopListDao: ShopListDao

    @Inject
    lateinit var mapper : ShopListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(authorities, "shop_items", GET_SHOP_ITEM_QUERY)
        addURI(authorities, "shop_items/#", GET_SHOP_ITEM_BY_ID)
        addURI(authorities, "shop_items/*", GET_SHOP_ITEM_BY_NAME)
    }

    fun log(text : String){
        Log.d(COMMON_TAG, text)
    }
    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

       val code = uriMatcher.match(uri)
       log("query $uri code = $code" )
       return when(code){
            GET_SHOP_ITEM_QUERY -> {
                 shopListDao.getShopListCursor()
            }
           else -> {
               null
           }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val code = uriMatcher.match(uri)
        log("query $uri code = $code" )
        when(code) {
            GET_SHOP_ITEM_QUERY -> {
                if(values == null){
                    return null
                }

                val id = values.getAsInteger(KEY_ID)
                val name = values.getAsString(KEY_NAME)
                val count = values.getAsInteger(KEY_COUNT)
                val enabled = values.getAsBoolean(KEY_ENABLED)

                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )

                shopListDao.addShopItemSync(mapper.mapEntityToDbModel(shopItem))

            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object{
        const val authorities = "com.example.shoppinglist"
        private const val GET_SHOP_ITEM_QUERY = 100
        private const val GET_SHOP_ITEM_BY_ID = 101
        private const val GET_SHOP_ITEM_BY_NAME = 102

        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_COUNT = "count"
        const val KEY_ENABLED = "enabled"
    }
}