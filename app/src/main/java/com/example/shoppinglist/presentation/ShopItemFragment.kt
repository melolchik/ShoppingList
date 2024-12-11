package com.example.shoppinglist.presentation

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShopListProvider
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.di.ViewModelFactory
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopItemFragment : Fragment() {
    private var _binding : FragmentShopItemBinding? = null
    private val binding : FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding = null")

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var onEditingFinishedListener : OnEditingFinishedListener
    private var screenMode = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    private val component by lazy {
        (this.requireActivity().application as ShoppingListApp).component
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    fun log(text: String){
        Log.d(COMMON_TAG,"$TAG $screenMode: $text")
    }


    override fun onResume() {
        log("onResume")
        super.onResume()
    }

    override fun onPause() {
        log("onPause")
        super.onPause()
    }

    override fun onStart() {
        log("onStart")
        super.onStart()
    }

    override fun onStop() {
        log("onStop")
        super.onStop()
    }
    override fun onDestroyView() {
        log("onDestroyView")
        super.onDestroyView()
    }

    override fun onDetach() {
        log("onDetach")
        super.onDetach()
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        log("onAttach")
        component.inject(this)
        super.onAttach(context)
        if(context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        }else{
            throw RuntimeException("Activity must implement listener")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")
        parseArguments()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("onCreateView")
        _binding = FragmentShopItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("onViewCreated")
        viewModel = ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        initErrors()

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }

        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()

        }

    }

    private fun initErrors() {

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
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

        viewModel.getShopItem(shopItemId)
        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(binding.etName.text.toString(), binding.etCount.text.toString())
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            val id = 0
            val name = binding.etName.text.toString()
            val count = binding.etCount.text.toString()
            val enabled = true
            //viewModel.addShopItem(binding.etName.text.toString(), binding.etCount.text.toString())
            thread {
                context?.contentResolver?.insert(Uri.parse("content://com.example.shoppinglist/shop_items"),
                    ContentValues().apply {
                        put(ShopListProvider.KEY_ID, id)
                        put(ShopListProvider.KEY_NAME, name)
                        put(ShopListProvider.KEY_COUNT, count.toInt())
                        put(ShopListProvider.KEY_ENABLED, enabled)
                    }
                )
            }
        }
    }

    private fun parseArguments() {

        val args = requireArguments()

        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen_mode is absent")
        }
        val mode = args.getString(SCREEN_MODE, MODE_UNKNOWN)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }


    interface OnEditingFinishedListener{
        fun onEditingFinished()
    }

    companion object {
        public const val TAG = "ShopItemFragment"
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem() : ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }

        }

        fun newInstanceEditItem(shopItemId : Int) : ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }

        }
    }
}