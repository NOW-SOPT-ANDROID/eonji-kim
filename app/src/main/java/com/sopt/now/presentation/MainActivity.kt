package com.sopt.now.presentation

import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        initResultLoginUserInformation()
    }

    private fun initResultLoginUserInformation() {
        with(binding) {
            tvMainNicknameValue.text = intent.getStringExtra("nickname")!!
            tvMainAgeValue.text = intent.getStringExtra("age")!!
            tvMainIdValue.text = intent.getStringExtra("id")!!
            tvMainPwValue.text = intent.getStringExtra("pw")!!
        }
    }
}