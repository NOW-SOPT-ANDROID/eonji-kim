package com.sopt.now.presentation

import androidx.activity.OnBackPressedCallback
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.toast
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.presentation.model.User
import com.sopt.now.presentation.util.KeyStorage.USER_DATA

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var userData: User? = null
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
                toast(getString(R.string.toast_main_back))
            }
        }
    }

    private fun initResultLoginUserInformation() {
        with(binding) {
            userData = intent?.getParcelableExtra(USER_DATA)

            tvMainNicknameValue.text = userData?.nickname
            tvMainAgeValue.text = userData?.age + "세"
            tvMainIdValue.text = userData?.id
            tvMainPwValue.text = userData?.pw
        }
    }
}