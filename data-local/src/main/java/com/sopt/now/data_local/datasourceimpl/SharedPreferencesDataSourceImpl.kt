package com.sopt.now.data_local.datasourceimpl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sopt.now.data.datasource.SharedPreferenceDataSource
import javax.inject.Inject

class SharedPreferencesDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SharedPreferenceDataSource {
    override var memberId: Int
        get() = sharedPreferences.getInt(MEMBER_ID, -1)
        set(value) = sharedPreferences.edit { putInt(MEMBER_ID, value) }

    companion object {
        const val MEMBER_ID = "member_id"
    }
}