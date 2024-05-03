package com.sopt.now.compose.data

import android.content.Context
import android.content.SharedPreferences
import com.sopt.now.compose.data.KeyStorage.MEMBER_ID

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    private fun getString(key: String, defValue: String): String =
        prefs.getString(key, defValue).toString()

    private fun getInt(key: String, defValue: Int): Int = prefs.getInt(key, defValue)

    private fun setString(key: String, str: String) = prefs.edit().putString(key, str).apply()
    private fun setInt(key: String, value: Int) = prefs.edit().putInt(key, value).apply()

    fun getUser() = getInt(MEMBER_ID, -1)

    fun setUser(memberId: Int) {
        setInt(MEMBER_ID, memberId)
    }
}