package com.example.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("errorInputName")
fun bindErrorInputName(inputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) inputLayout.context.getString(R.string.error_input_name) else null
    inputLayout.error = message

}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(inputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) inputLayout.context.getString(R.string.error_input_count) else null
    inputLayout.error = message

}
