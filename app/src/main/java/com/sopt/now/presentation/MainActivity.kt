package com.sopt.now.presentation

import android.content.Intent
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.presentation.login.LoginActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        navigateToLogin()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}