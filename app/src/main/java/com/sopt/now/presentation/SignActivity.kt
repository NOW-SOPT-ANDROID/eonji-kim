package com.sopt.now.presentation

import android.content.Intent
import androidx.core.os.bundleOf
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.databinding.ActivitySignBinding

class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    override fun initView() {
        initSignBtnClickListener()
    }

    private fun initSignCheck() {
        with(binding) {
            when {
                etSignId.text.trim().length !in 6..10 -> snackBar(root) { getString(R.string.error_message_sign_invalid_id) }
                etSignPw.text.trim().length !in 8..12 -> snackBar(root) { getString(R.string.error_message_sign_invalid_pw) }
                etSignNickname.text.trim()
                    .isEmpty() -> snackBar(root) { getString(R.string.error_message_sign_invalid_nickname) }

                etSignAge.text.trim().length !in 1..3 -> snackBar(root) { getString(R.string.error_message_sign_invalid_age) }

                else -> navigateToLogin()
            }
        }
    }

    private fun ActivitySignBinding.navigateToLogin() {
        val intent = Intent(this@SignActivity, LoginActivity::class.java).apply {
            putExtras(
                bundleOf(
                    "id" to etSignId.text.toString(),
                    "pw" to etSignPw.text.toString(),
                    "nickname" to etSignNickname.text.toString(),
                    "age" to etSignAge.text.toString()
                )
            )
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initSignBtnClickListener() {
        binding.btnSignSign.setOnClickListener {
            initSignCheck()
        }
    }
}