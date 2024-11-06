package com.example.shoppinglist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {
    private lateinit var viewModel: ShopItemViewModel
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button
    private var screenMode = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop_item, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseIntent()
        initViews(view)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initErrors()

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            finish()
        }

        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()

        }

    }

    private fun initErrors() {

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) "Name error" else null
            tilName.error = message
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) "Count error" else null
            tilCount.error = message

        }
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun launchEditMode() {
        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())

        }
        viewModel.getShopItem(shopItemId)
        btnSave.setOnClickListener {
            viewModel.editShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun launchAddMode() {
        btnSave.setOnClickListener {
            viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen_mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        with(view) {
            tilName = findViewById(R.id.til_name)
            tilCount = findViewById(R.id.til_count)
            etName = findViewById(R.id.et_name)
            etCount = findViewById(R.id.et_count)
            btnSave = findViewById(R.id.save_button)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""
    }
}