package com.sopt.now.presentation.mypage

import androidx.fragment.app.viewModels
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.util.fragment.snackBar
import com.sopt.now.core.util.view.UiState
import com.sopt.now.data.dto.response.ResponseGetUserInfoDataDto
import com.sopt.now.databinding.FragmentMyPageBinding
import timber.log.Timber

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun initView() {
        initObserveUserInfo()
    }

    private fun initObserveUserInfo() {
        myPageViewModel.getUserInfo.observe(this) {
            when (it) {
                is UiState.Success -> initResultLoginUserInformation(it.data.data)
                is UiState.Failure -> initErrorMessage(it.errorMessage)
                is UiState.Loading -> Timber.d("로딩중")
            }
        }
    }

    private fun initErrorMessage(errorMessage: String) {
        snackBar(binding.root) { "유저조회 실패 : $errorMessage" }
    }

    private fun initResultLoginUserInformation(data: ResponseGetUserInfoDataDto) {
        with(binding) {
            tvMyPageMemberIdValue.text = "${myPageViewModel.getUserMemberId()}번"
            tvMyPageNicknameValue.text = data.nickname
            tvMyPageIdValue.text = data.authenticationId
            tvMyPageTelValue.text = data.phone
        }
    }
}