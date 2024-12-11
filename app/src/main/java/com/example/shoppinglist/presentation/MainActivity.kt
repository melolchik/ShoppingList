package com.example.shoppinglist.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShopListProvider
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.di.ViewModelFactory
import com.example.shoppinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

const val COMMON_TAG = "COMMON"
class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    private val component by lazy {
        (this.application as ShoppingListApp).component
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory



    fun log(text: String){
        Log.d(COMMON_TAG,"$TAG: $text")
    }
    override fun onResume() {
        log("onResume")
        super.onResume()
    }

    override fun onRestart() {
        log("onRestart")
        super.onRestart()
    }

    override fun onPause() {
        log("onPause")
        super.onPause()
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }

    override fun onStart() {
        log("onStart")
        super.onStart()
    }

    override fun onStop() {
        log("onStop")
        super.onStop()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        log("onCreate")
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            log("Observe = $it")
            shopListAdapter.submitList(it)
        }
        val buttonAddItem = binding.buttonAddShopItem
        buttonAddItem.setOnClickListener {
            if(isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }


        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.example.shoppinglist/shop_items"),
                null,
                null,
                null,
                null
            )
            log("cursor = $cursor")
            while(cursor?.moveToNext() == true){
                log("${cursor.columnCount}")

                for(names in cursor.columnNames){
                    log("columnNames = ${names}")
                }

                val id = cursor.getInt(cursor.getColumnIndexOrThrow(ShopListProvider.KEY_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(ShopListProvider.KEY_NAME))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow(ShopListProvider.KEY_COUNT))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow(ShopListProvider.KEY_ENABLED)) > 0

                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )

                log("shopItem = $shopItem")
            }

            cursor?.close()
        }


        contentResolver.query(
            Uri.parse("content://com.example.shoppinglist/shop_items/3"),
            null,
            null,
            null,
            null
        )

        contentResolver.query(
            Uri.parse("content://com.example.shoppinglist/shop_items/Jonh"),
            null,
            null,
            null,
            null
        )
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity,"Success",Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode() : Boolean = binding.shopItemContainer == null

    private fun launchFragment(fragment: Fragment) {

        //remove fragment
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()

        FragmentManager.POP_BACK_STACK_INCLUSIVE
    }

    private fun setupRecyclerView() {
        val rwShopList = binding.rvShopList
        with(rwShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ACTIVE,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_INACTIVE,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rwShopList)

    }

    private fun setupSwipeListener(rwShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                //viewModel.deleteShopItem(item)
                thread {
                    contentResolver.delete(
                        Uri.parse("content://com.example.shoppinglist/shop_items"),
                        "",
                        arrayOf(item.id.toString())
                    )
                }
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rwShopList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if(isOnePaneMode()){
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    companion object{
        const val TAG = "MainActivity"
    }
}