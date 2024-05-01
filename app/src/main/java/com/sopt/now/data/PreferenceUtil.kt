package com.sopt.now.data

import android.content.Context
import android.content.SharedPreferences
import com.sopt.now.presentation.model.User
import com.sopt.now.presentation.util.KeyStorage.USER_AGE
import com.sopt.now.presentation.util.KeyStorage.USER_ID
import com.sopt.now.presentation.util.KeyStorage.USER_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.USER_PW

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    private fun getString(key: String, defValue: String): String = prefs.getString(key, defValue).toString()

    private fun setString(key: String, str: String) = prefs.edit().putString(key, str).apply()

    fun getUser(): User {
        val id = getString(USER_ID, "")
        val pwd = getString(USER_PW, "")
        val name = getString(USER_NICKNAME, "")
        val age = getString(USER_AGE, "")

        return User(id, pwd, name, age)
    }

    fun setUser(user: User) {
        setString(USER_ID, user.id)
        setString(USER_PW, user.pw)
        setString(USER_NICKNAME, user.nickname)
        setString(USER_AGE, user.age)
    }
}