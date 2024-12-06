package com.example.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class ShopListProvider : ContentProvider() {

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(authorities, "shop_items", GET_SHOP_ITEM_QUERY)
        addURI(authorities, "shop_items/#", GET_SHOP_ITEM_BY_ID)
        addURI(authorities, "shop_items/*", GET_SHOP_ITEM_BY_NAME)
    }

    fun log(text : String){
        Log.d("ShopListProvider", text)
    }
    override fun onCreate(): Boolean {
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
        when(code){
            GET_SHOP_ITEM_QUERY -> {

            }
        }
        log("query $uri code = $code" )
        return null
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
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
    }
}