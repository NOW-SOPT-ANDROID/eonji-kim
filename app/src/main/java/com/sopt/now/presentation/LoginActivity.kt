package com.sopt.now.presentation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.presentation.util.KeyStorage.USER_AGE
import com.sopt.now.presentation.util.KeyStorage.USER_ID
import com.sopt.now.presentation.util.KeyStorage.USER_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.USER_PW

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private var userId: String? = null
    private var userPw: String? = null
    private var userNickname: String? = null
    private var userAge: String? = null

    override fun initView() {
        initResultSignUserInformation()
        initLoginBtnClickListener()
        initSignBtnClickListener()
    }

    private fun initResultSignUserInformation() {
        getResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            result.data?.run {
                if (result.resultCode == RESULT_OK) {
                    userId = getStringExtra(USER_ID)
                    userPw = getStringExtra(USER_PW)
                    userNickname = getStringExtra(USER_NICKNAME)
                    userAge = getStringExtra(USER_AGE)

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

    private fun checkLoginValid() {
        with(binding) {
            if (getLoginValidity(etLoginId.text.toString(), etLoginPw.text.toString())) {
                toast(getString(R.string.success_message_login_login))

                navigateToMain()
            } else snackBar(root) { getString(R.string.error_message_login_login) }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
            putExtras(
                bundleOf(
                    USER_ID to userId,
                    USER_PW to userPw,
                    USER_NICKNAME to userNickname,
                    USER_AGE to userAge
                )
            )
        }
        startActivity(intent)
    }

    private fun getLoginValidity(id: String, pw: String): Boolean {
        return id.trim().isNotEmpty() && id == userId && pw.trim().isNotEmpty() && pw == userPw
    }

    private fun initLoginBtnClickListener() {
        binding.btnLoginLogin.setOnClickListener {
            checkLoginValid()
        }
    }
}