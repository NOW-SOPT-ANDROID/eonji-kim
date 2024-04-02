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

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private var useId: String? = null
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
            if (result.resultCode == RESULT_OK) {
                useId = result.data?.getStringExtra("id")
                userPw = result.data?.getStringExtra("pw")
                userNickname = result.data?.getStringExtra("nickname")
                userAge = result.data?.getStringExtra("age")

                toast(getString(R.string.success_message_login_sign))
            } else snackBar(binding.root) { getString(R.string.error_message_login_sign) }
        }
    }

    private fun initSignBtnClickListener() {
        binding.btnLoginSign.setOnClickListener {
            getResult.launch(Intent(this, SignActivity::class.java))
        }
    }

    private fun initLoginCheck() {
        with(binding) {
            if (isLoginValidity(etLoginId.text.toString(), etLoginPw.text.toString())) {
                toast(getString(R.string.success_message_login_login))

                navigateToMain()
            } else snackBar(root) { getString(R.string.error_message_login_login) }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
            putExtras(
                bundleOf(
                    "id" to useId,
                    "pw" to userPw,
                    "nickname" to userNickname,
                    "age" to userAge
                )
            )
        }
        startActivity(intent)
    }

    private fun isLoginValidity(id: String, pw: String): Boolean {
        return id.trim().isNotEmpty() && id == useId && pw.trim().isNotEmpty() && pw == userPw
    }

    private fun initLoginBtnClickListener() {
        binding.btnLoginLogin.setOnClickListener {
            initLoginCheck()
        }
    }
}