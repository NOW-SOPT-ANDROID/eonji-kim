package com.sopt.now.compose.core_ui.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.stringOf(
    @StringRes resId: Int,
) = getString(resId)

fun Context.colorOf(
    @ColorRes resId: Int,
) = ContextCompat.getColor(this, resId)

fun Context.drawableOf(
    @DrawableRes resId: Int,
) = ContextCompat.getDrawable(this, resId)

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.statusBarColorOf(
    @ColorRes resId: Int,
) {
    if (this is Activity) {
        window?.statusBarColor = colorOf(resId)
    }
}