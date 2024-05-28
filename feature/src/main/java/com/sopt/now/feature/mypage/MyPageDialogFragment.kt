package com.sopt.now.feature.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import com.sopt.now.core_ui.base.BindingDialogFragment
import com.sopt.now.core_ui.util.context.dialogFragmentResize
import com.sopt.now.core_ui.util.fragment.snackBar
import com.sopt.now.core_ui.util.fragment.viewLifeCycle
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.FragmentMyPageDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
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
                    snackBar(binding.root) { "비밀번호 변경 완료 : ${it.data}" }
                    dismiss()
                }

                is UiState.Failure -> snackBar(binding.root) { it.errorMessage }
                is UiState.Loading -> Timber.d("로딩중")
                is UiState.Empty -> Timber.d("empty")
            }
        }
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
