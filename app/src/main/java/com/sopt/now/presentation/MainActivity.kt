package com.sopt.now.presentation

import androidx.activity.OnBackPressedCallback
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.toast
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.presentation.util.KeyStorage.USER_AGE
import com.sopt.now.presentation.util.KeyStorage.USER_ID
import com.sopt.now.presentation.util.KeyStorage.USER_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.USER_PW

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var backPressedTime = 0L

    override fun initView() {
        initResultLoginUserInformation()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (System.currentTimeMillis() - backPressedTime <= 2000) {
                finish()
            } else {
                backPressedTime = System.currentTimeMillis()
                toast("종료하시려면 한 번 더 눌러주세요")
            }
        }
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