package com.sopt.now.data

import android.content.Context
import android.content.SharedPreferences
import com.sopt.now.presentation.util.KeyStorage.MEMBER_ID

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    private fun getInt(key: String, defValue: Int): Int = prefs.getInt(key, defValue)

    private fun setInt(key: String, value: Int) = prefs.edit().putInt(key, value).apply()

    fun getUser() = getInt(MEMBER_ID, -1)

    fun setUser(memberId: Int) {
        setInt(MEMBER_ID, memberId)
    }
}