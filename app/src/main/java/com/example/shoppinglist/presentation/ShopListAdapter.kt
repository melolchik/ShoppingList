package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnableBinding
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_ACTIVE = 1
        const val VIEW_TYPE_INACTIVE = 0
        const val MAX_POOL_SIZE = 5
    }

    var count = 0
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout =
            when(viewType){
                VIEW_TYPE_ACTIVE -> R.layout.item_shop_enable
                VIEW_TYPE_INACTIVE -> R.layout.item_shop_disabled
                else -> throw RuntimeException("Unknown view type = $viewType")
            }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
            layout,
            parent,
            false)
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder count = ${++count}")
        val shopItem = getItem(position)
        val binding = holder.binding

        when(binding){
            is ItemShopEnableBinding ->{
                binding.shopItem = shopItem
            }
            is ItemShopDisabledBinding ->{
                binding.shopItem = shopItem
            }
        }


        binding.root.setOnLongClickListener() {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener() {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) VIEW_TYPE_ACTIVE else VIEW_TYPE_INACTIVE
    }


}