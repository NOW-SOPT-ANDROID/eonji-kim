package com.sopt.now.feature

import android.content.Intent
import com.sopt.now.core_ui.base.BindingActivity
import com.sopt.now.feature.databinding.ActivityMainBinding
import com.sopt.now.feature.login.LoginActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        navigateToLogin()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}