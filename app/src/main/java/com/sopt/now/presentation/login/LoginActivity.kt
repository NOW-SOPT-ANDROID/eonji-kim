package com.sopt.now.presentation.login

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.presentation.home.HomeActivity
import com.sopt.now.presentation.sign.SignActivity
import com.sopt.now.presentation.util.KeyStorage.ERROR_LOGIN_ID_PW

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
        loginViewModel.postLogin.observe(this) {
            when (it.isSuccess) {
                true -> navigateToHome(it.message)
                false -> initErrorMessage(it.message)
            }
        }
    }

    private fun initErrorMessage(errorMessage: String) {
        when (errorMessage) {
            ERROR_LOGIN_ID_PW -> snackBar(binding.root) { getString(R.string.error_message_login_login) }
            else -> snackBar(binding.root) { "로그인 실패 : $errorMessage" }
        }
    }

    private fun navigateToHome(message: String) {
        toast(message)
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