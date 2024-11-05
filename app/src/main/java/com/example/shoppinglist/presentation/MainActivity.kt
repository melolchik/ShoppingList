package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    private val TAG = "ShoppingList"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
           //Log.d(TAG, "Observe = $it")
            adapter.shopList = it
        }
    }

    fun setupRecyclerView(){
        val rwShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rwShopList.adapter = adapter
        rwShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ACTIVE,ShopListAdapter.MAX_POOL_SIZE)
        rwShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_INACTIVE,ShopListAdapter.MAX_POOL_SIZE)

    }
}