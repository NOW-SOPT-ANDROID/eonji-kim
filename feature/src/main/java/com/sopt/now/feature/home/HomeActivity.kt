package com.sopt.now.feature.home

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.sopt.now.core_ui.base.BindingActivity
import com.sopt.now.core_ui.util.context.toast
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.ActivityHomeBinding
import com.sopt.now.feature.mypage.MyPageFragment
import com.sopt.now.feature.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private var backPressedTime = 0L

    override fun initView() {
        initCurrentFragment()
        initBottomNavClickListener()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun initCurrentFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(binding.fcvHome.id)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction().add(R.id.fcv_home, HomeFragment()).commit()
        }
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

    private fun initBottomNavClickListener() {
        binding.bnvHome.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_search -> {
                    replaceFragment(SearchFragment())
                    true
                }

                R.id.menu_mypage -> {
                    replaceFragment(MyPageFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_home, fragment)
            .commit()
    }
}