package com.sopt.now.feature.sign

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.core_ui.base.BindingActivity
import com.sopt.now.core_ui.util.context.snackBar
import com.sopt.now.core_ui.util.context.toast
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.feature.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.feature.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.feature.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.feature.KeyStorage.ERROR_SIGN_TEL
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.ActivitySignBinding
import com.sopt.now.feature.home.user.User
import com.sopt.now.feature.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    private val signViewModel by viewModels<SignViewModel>()

    override fun initView() {
        initSignBtnClickListener()
        initObserveSign()
    }

    private fun initObserveSign() {
        signViewModel.postSign.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> navigateToLogin(it.data)
                is UiState.Failure -> initErrorMessage(it.errorMessage)
                is UiState.Loading -> Timber.d("로딩중")
                is UiState.Empty -> Timber.d("empty")
            }
        }.launchIn(lifecycleScope)
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

    private fun navigateToLogin(userId: Int) {
        toast("회원가입에 성공했습니다! userID는 $userId")
        setResult(RESULT_OK, Intent(this@SignActivity, LoginActivity::class.java))
        finish()
    }

    private fun initSignBtnClickListener() {
        with(binding) {
            btnSignSign.setOnClickListener {
                signViewModel.postSign(
                    UserEntity(
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