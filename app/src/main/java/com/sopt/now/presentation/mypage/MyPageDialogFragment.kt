package com.sopt.now.presentation.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.R
import com.sopt.now.core.base.BindingDialogFragment
import com.sopt.now.core.util.context.dialogFragmentResize
import com.sopt.now.core.util.fragment.snackBar
import com.sopt.now.core.util.fragment.viewLifeCycle
import com.sopt.now.core.util.fragment.viewLifeCycleScope
import com.sopt.now.core.util.view.UiState
import com.sopt.now.databinding.FragmentMyPageDialogBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MyPageDialogFragment() :
    BindingDialogFragment<FragmentMyPageDialogBinding>(R.layout.fragment_my_page_dialog) {
    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun initView() {
        initCancelBtnClickListener()
        initChangeBtnClickListener()
        initObserveUserPw()
    }

    override fun onResume() {
        context?.dialogFragmentResize(this, 25.0f)
        super.onResume()
    }

    private fun initObserveUserPw() {
        myPageViewModel.patchUserPw.flowWithLifecycle(viewLifeCycle).onEach {
            when (it) {
                is UiState.Success -> {
                    snackBar(binding.root) { "비밀번호 변경 완료 : ${it.data.message}" }
                    dismiss()
                }

                is UiState.Failure -> snackBar(binding.root) { it.errorMessage }
                is UiState.Loading -> Timber.d("로딩중")
            }
        }.launchIn(viewLifeCycleScope)
    }

    private fun initCancelBtnClickListener() {
        binding.btnMyPageDialogPwChangeCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initChangeBtnClickListener() {
        with(binding) {
            btnMyPageDialogPwChange.setOnClickListener {
                myPageViewModel.patchUserPw(
                    tfMyPageDialogPwBeforeEt.text.toString(),
                    tfMyPageDialogPwAfterEt.text.toString(),
                    tfMyPageDialogPwCheckEt.text.toString()
                )
            }
        }
    }

}
