package com.example.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.domain.ShopItem

class ShopListDiffCallback(
    private val oldList : List<ShopItem>,
    private val newList : List<ShopItem>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int  = oldList.size

    override fun getNewListSize(): Int  = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem : ShopItem = oldList[oldItemPosition]
        val newItem : ShopItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem : ShopItem = oldList[oldItemPosition]
        val newItem : ShopItem = newList[newItemPosition]
        return oldItem == newItem
    }

}