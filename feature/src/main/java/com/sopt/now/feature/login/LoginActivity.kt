package com.sopt.now.feature.login

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.core_ui.base.BindingActivity
import com.sopt.now.core_ui.util.context.snackBar
import com.sopt.now.core_ui.util.context.toast
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.feature.KeyStorage.ERROR_LOGIN_ID_PW
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.ActivityLoginBinding
import com.sopt.now.feature.home.HomeActivity
import com.sopt.now.feature.sign.SignActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun initView() {
        initResultSignUserInformation()
        initSignBtnClickListener()
        initLoginBtnClickListener()
        initObserveLogin()
    }

    private fun initResultSignUserInformation() {
        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            result.data?.run {
                if (result.resultCode != RESULT_OK) {
                    snackBar(binding.root) { getString(R.string.error_message_login_sign) }
                }
            }
        }
    }

    private fun initSignBtnClickListener() {
        binding.btnLoginSign.setOnClickListener {
            getResult.launch(Intent(this, SignActivity::class.java))
        }
    }

    private fun initObserveLogin() {
        loginViewModel.postLogin.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Success -> navigateToHome(it.data)
                is UiState.Failure -> initErrorMessage(it.errorMessage)
                is UiState.Loading -> Timber.d("로딩중")
                is UiState.Empty -> Timber.d("empty")
            }
        }.launchIn(lifecycleScope)
    }

    private fun initErrorMessage(errorMessage: String) {
        when (errorMessage) {
            ERROR_LOGIN_ID_PW -> snackBar(binding.root) { getString(R.string.error_message_login_login) }
            else -> snackBar(binding.root) { "로그인 실패 : $errorMessage" }
        }
    }

    private fun navigateToHome(userId: Int) {
        toast("로그인에 성공했습니다! userID는 $userId")
        Intent(this@LoginActivity, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.let { startActivity(it) }
    }

    private fun initLoginBtnClickListener() {
        with(binding) {
            btnLoginLogin.setOnClickListener {
                loginViewModel.postLogin(
                    etLoginId.text.toString(),
                    etLoginPw.text.toString()
                )
            }
        }
    }
}