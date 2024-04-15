package com.sopt.now.presentation.mypage

import androidx.fragment.app.viewModels
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.presentation.login.LoginViewModel

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun initView() {
        initResultLoginUserInformation()
    }

    private fun initResultLoginUserInformation() {
        with(binding) {
            tvMyPageNicknameValue.text = loginViewModel.getUserInfo().nickname
            tvMyPageAgeValue.text = loginViewModel.getUserInfo().age + "세"
            tvMyPageIdValue.text = loginViewModel.getUserInfo().id
            tvMyPagePwValue.text = loginViewModel.getUserInfo().pw
        }
    }
}