package com.sopt.now.presentation.sign

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.view.UiState
import com.sopt.now.databinding.ActivitySignBinding
import com.sopt.now.presentation.login.LoginActivity
import com.sopt.now.presentation.model.User
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_AGE
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_PW
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    private val signViewModel by viewModels<SignViewModel>()

    override fun initView() {
        initSignBtnClickListener()
        initObserveSignValid()
    }

    private fun initObserveSignValid() {
        signViewModel.signValid.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Loading -> Unit
                is UiState.Success -> {
                    navigateToLogin(it.data)
                }

                is UiState.Failure -> initErrorMessage(it.errorMessage)
            }
        }.launchIn(lifecycleScope)
    }

    private fun initErrorMessage(errorMessage: String) {
        when (errorMessage) {
            ERROR_SIGN_ID -> snackBar(binding.root) { getString(R.string.error_message_sign_invalid_id) }
            ERROR_SIGN_PW -> snackBar(binding.root) { getString(R.string.error_message_sign_invalid_pw) }
            ERROR_SIGN_NICKNAME -> snackBar(binding.root) { getString(R.string.error_message_sign_invalid_nickname) }
            ERROR_SIGN_AGE -> snackBar(binding.root) { getString(R.string.error_message_sign_invalid_age) }
        }
    }

    private fun navigateToLogin(user: User) {
        setResult(RESULT_OK, Intent(this@SignActivity, LoginActivity::class.java))
        signViewModel.setUserInfo(user)
        finish()
    }

    private fun initSignBtnClickListener() {
        with(binding) {
            btnSignSign.setOnClickListener {
                signViewModel.checkSignValid(
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