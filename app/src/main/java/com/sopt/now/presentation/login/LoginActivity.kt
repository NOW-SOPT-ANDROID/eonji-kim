package com.sopt.now.presentation.login

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.core.util.view.UiState
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.presentation.home.HomeActivity
import com.sopt.now.presentation.sign.SignActivity
import com.sopt.now.presentation.util.KeyStorage.ERROR_LOGIN_ID_PW
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun initView() {
        initResultSignUserInformation()
        initLoginBtnClickListener()
        initObserveLoginValid()
        initSignBtnClickListener()
    }

    private fun initResultSignUserInformation() {
        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            result.data?.run {
                if (result.resultCode == RESULT_OK) {
                    toast(getString(R.string.success_message_login_sign))
                } else snackBar(binding.root) { getString(R.string.error_message_login_sign) }
            }
        }
    }

    private fun initSignBtnClickListener() {
        binding.btnLoginSign.setOnClickListener {
            getResult.launch(Intent(this, SignActivity::class.java))
        }
    }

    private fun initObserveLoginValid() {
        loginViewModel.loginValid.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is UiState.Loading -> Unit
                is UiState.Success -> navigateToHome()
                is UiState.Failure -> initErrorMessage(it.errorMessage)
            }
        }.launchIn(lifecycleScope)
    }

    private fun initErrorMessage(errorMessage: String) {
        when (errorMessage) {
            ERROR_LOGIN_ID_PW -> snackBar(binding.root) { getString(R.string.error_message_login_login) }
        }
    }

    private fun navigateToHome() {
        toast(getString(R.string.success_message_login_login))

        Intent(this@LoginActivity, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.let { startActivity(it) }
    }

    private fun initLoginBtnClickListener() {
        with(binding) {
            btnLoginLogin.setOnClickListener {
                loginViewModel.checkLoginValid(
                    etLoginId.text.toString(),
                    etLoginPw.text.toString()
                )
            }
        }
    }
}