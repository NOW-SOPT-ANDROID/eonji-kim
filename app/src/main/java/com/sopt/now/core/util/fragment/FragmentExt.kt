package com.sopt.now.core.util.fragment

import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.sopt.now.core.util.context.statusBarColorOf

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.snackBar(
    anchorView: View,
    message: () -> String
) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun Fragment.stringOf(
    @StringRes resId: Int
) = getString(resId)

fun Fragment.colorOf(
    @ColorRes resId: Int
) = ContextCompat.getColor(requireContext(), resId)

fun Fragment.drawableOf(
    @DrawableRes resId: Int
) = ContextCompat.getDrawable(requireContext(), resId)

val Fragment.viewLifeCycle
    get() = viewLifecycleOwner.lifecycle

val Fragment.viewLifeCycleScope
    get() = viewLifecycleOwner.lifecycleScope

fun Fragment.statusBarColorOf(
    @ColorRes resId: Int
) {
    requireActivity().statusBarColorOf(resId)
}
