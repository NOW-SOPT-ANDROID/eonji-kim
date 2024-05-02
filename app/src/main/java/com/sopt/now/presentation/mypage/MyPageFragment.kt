package com.sopt.now.presentation.mypage

import android.graphics.Paint
import androidx.fragment.app.viewModels
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.util.fragment.snackBar
import com.sopt.now.core.util.view.UiState
import com.sopt.now.data.dto.response.ResponseUserInfoDataDto
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.presentation.util.DialogTag.CHANGE_PW
import timber.log.Timber

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun initView() {
        initPwChangeTvUnderline()
        initObserveUserInfo()
        initPwChangeTvClickListener()
    }

    private fun initPwChangeTvUnderline() {
        binding.tvMyPagePwChange.paintFlags = Paint.UNDERLINE_TEXT_FLAG
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

    private fun initResultLoginUserInformation(data: ResponseUserInfoDataDto) {
        with(binding) {
            tvMyPageMemberIdValue.text = "${myPageViewModel.getUserMemberId()}번"
            tvMyPageNicknameValue.text = data.nickname
            tvMyPageIdValue.text = data.authenticationId
            tvMyPageTelValue.text = data.phone
        }
    }

    private fun initPwChangeTvClickListener() {
        binding.tvMyPagePwChange.setOnClickListener {
            MyPageDialogFragment().show(childFragmentManager, CHANGE_PW)
        }
    }
}