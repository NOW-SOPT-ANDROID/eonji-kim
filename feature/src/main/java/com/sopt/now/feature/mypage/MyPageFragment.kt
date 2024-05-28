package com.sopt.now.feature.mypage

import android.graphics.Paint
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import com.sopt.now.core_ui.base.BindingFragment
import com.sopt.now.core_ui.util.fragment.snackBar
import com.sopt.now.core_ui.util.fragment.viewLifeCycle
import com.sopt.now.core_ui.util.fragment.viewLifeCycleScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.entity.UserInfoEntity
import com.sopt.now.feature.DialogTag.CHANGE_PW
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
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
        myPageViewModel.getUserInfo.flowWithLifecycle(viewLifeCycle).onEach {
            when (it) {
                is UiState.Success -> initResultLoginUserInformation(it.data)
                is UiState.Failure -> initErrorMessage(it.errorMessage)
                is UiState.Loading -> Timber.d("로딩중")
                is UiState.Empty -> Timber.d("empty")
            }
        }.launchIn(viewLifeCycleScope)
    }

    private fun initErrorMessage(errorMessage: String) {
        snackBar(binding.root) { "유저조회 실패 : $errorMessage" }
    }

    private fun initResultLoginUserInformation(data: UserInfoEntity) {
        with(binding) {
            tvMyPageMemberIdValue.text = "${data.memberId}번"
            tvMyPageNicknameValue.text = data.nickname
            tvMyPageIdValue.text = data.id
            tvMyPageTelValue.text = data.tel
        }
    }

    private fun initPwChangeTvClickListener() {
        binding.tvMyPagePwChange.setOnClickListener {
            MyPageDialogFragment().show(childFragmentManager, CHANGE_PW)
        }
    }
}