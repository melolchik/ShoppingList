package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.shop_item_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        parseIntent()
//        initViews()
//
//        initErrors()
//
//        viewModel.shouldCloseScreen.observe(this){
//            finish()
//        }
//
//        when(screenMode){
//            MODE_EDIT -> launchEditMode()
//            MODE_ADD -> launchAddMode()
//
//        }


    }

//    private fun initErrors() {
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) "Name error" else null
//            tilName.error = message
//        }
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) "Count error" else null
//            tilCount.error = message
//
//        }
//        etName.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//        })
//
//        etCount.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//        })
//    }
//
//    private fun launchEditMode(){
//        viewModel.shopItem.observe(this){
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//
//        }
//        viewModel.getShopItem(shopItemId)
//        btnSave.setOnClickListener{
//            viewModel.editShopItem(etName.text.toString(),etCount.text.toString())
//        }
//    }
//
//    private fun launchAddMode(){
//        btnSave.setOnClickListener{
//            viewModel.addShopItem(etName.text.toString(),etCount.text.toString())
//        }
//    }
//
//    private fun parseIntent() {
//        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
//            throw RuntimeException("Param screen_mode is absent")
//        }
//        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
//        if (mode != MODE_ADD && mode != MODE_EDIT) {
//            throw RuntimeException("Unknown screen mode $mode")
//        }
//        screenMode = mode
//
//        if (screenMode == MODE_EDIT){
//            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
//                throw RuntimeException("Shop item id is absent")
//            }
//            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID,ShopItem.UNDEFINED_ID)
//    }
//}
//
//private fun initViews() {
//    tilName = findViewById(R.id.til_name)
//    tilCount = findViewById(R.id.til_count)
//    etName = findViewById(R.id.et_name)
//    etCount = findViewById(R.id.et_count)
//    btnSave = findViewById(R.id.save_button)
//}

companion object {
    private const val EXTRA_SCREEN_MODE = "extra_mode"
    private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
    private const val MODE_ADD = "mode_add"
    private const val MODE_EDIT = "mode_edit"
    private const val MODE_UNKNOWN = ""

    fun newIntentAddItem(context: Context): Intent {
        val intent = Intent(context, ShopItemActivity::class.java)
        intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
        return intent
    }

    fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
        val intent = Intent(context, ShopItemActivity::class.java)
        intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
        intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
        return intent
    }
}
}