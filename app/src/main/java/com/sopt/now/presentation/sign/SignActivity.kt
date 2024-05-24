package com.sopt.now.presentation.sign

import android.content.Intent
import androidx.activity.viewModels
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.core.util.view.UiState
import com.sopt.now.databinding.ActivitySignBinding
import com.sopt.now.presentation.home.user.User
import com.sopt.now.presentation.login.LoginActivity
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_TEL
import timber.log.Timber

class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    private val signViewModel by viewModels<SignViewModel>()

    override fun initView() {
        initSignBtnClickListener()
        initObserveSign()
    }

    private fun initObserveSign() {
        signViewModel.postSign.observe(this) {
            when (it) {
                is UiState.Success -> navigateToLogin(it.data.toString())
                is UiState.Failure -> initErrorMessage(it.errorMessage)
                is UiState.Loading -> Timber.d("로딩중")
            }
        }
    }

    private fun initErrorMessage(errorMessage: String) {
        when (errorMessage) {
            ERROR_SIGN_ID -> snackBar(binding.root) { getString(R.string.error_message_sign_invalid_id) }
            ERROR_SIGN_PW -> snackBar(binding.root) { getString(R.string.error_message_sign_invalid_pw) }
            ERROR_SIGN_NICKNAME -> snackBar(binding.root) { getString(R.string.error_message_sign_invalid_nickname) }
            ERROR_SIGN_TEL -> snackBar(binding.root) { getString(R.string.error_message_sign_invalid_tel) }
            else -> snackBar(binding.root) { "회원가입 실패 : $errorMessage" }
        }
    }

    private fun navigateToLogin(message: String) {
        toast(message)
        setResult(RESULT_OK, Intent(this@SignActivity, LoginActivity::class.java))
        finish()
    }

    private fun initSignBtnClickListener() {
        with(binding) {
            btnSignSign.setOnClickListener {
                signViewModel.postSign(
                    User(
                        etSignId.text.toString(),
                        etSignPw.text.toString(),
                        etSignNickname.text.toString(),
                        etSignAge.text.toString(),
                    )
                )
            }
        }
    }
}