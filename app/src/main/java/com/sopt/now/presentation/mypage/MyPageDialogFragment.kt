package com.sopt.now.presentation.mypage

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.now.R
import com.sopt.now.core.base.BindingDialogFragment
import com.sopt.now.core.util.context.dialogFragmentResize
import com.sopt.now.core.util.fragment.snackBar
import com.sopt.now.core.util.view.UiState
import com.sopt.now.databinding.FragmentMyPageDialogBinding
import timber.log.Timber

class MyPageDialogFragment() :
    BindingDialogFragment<FragmentMyPageDialogBinding>(R.layout.fragment_my_page_dialog) {
    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }

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
        myPageViewModel.patchUserPw.observe(this) {
            when (it) {
                is UiState.Success -> {
                    initMessage("비밀번호 변경 완료 : ${it.data.message}")
                    dismiss()
                }

                is UiState.Failure -> initMessage(it.errorMessage)
                is UiState.Loading -> Timber.d("로딩중")
            }
        }
    }

    private fun initMessage(message: String) {
        snackBar(binding.root) { message }
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}
