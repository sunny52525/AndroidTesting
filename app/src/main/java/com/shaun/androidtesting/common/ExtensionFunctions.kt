package com.shaun.androidtesting.common

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message:String?){
    Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
}

fun Fragment.hideKeyboard(){
    requireActivity().currentFocus?.let { view ->
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

