package com.sopt.now.presentation

import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.presentation.util.KeyStorage.USER_AGE
import com.sopt.now.presentation.util.KeyStorage.USER_ID
import com.sopt.now.presentation.util.KeyStorage.USER_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.USER_PW

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        initResultLoginUserInformation()
    }

    private fun initResultLoginUserInformation() {
        with(binding) {
            tvMainNicknameValue.text = intent?.getStringExtra(USER_NICKNAME)
            tvMainAgeValue.text = intent?.getStringExtra(USER_AGE)
            tvMainIdValue.text = intent?.getStringExtra(USER_ID)
            tvMainPwValue.text = intent?.getStringExtra(USER_PW)
        }
    }
}