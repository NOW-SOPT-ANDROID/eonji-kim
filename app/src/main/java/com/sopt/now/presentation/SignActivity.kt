package com.sopt.now.presentation

import android.content.Intent
import androidx.core.os.bundleOf
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.databinding.ActivitySignBinding
import com.sopt.now.presentation.util.KeyStorage.USER_AGE
import com.sopt.now.presentation.util.KeyStorage.USER_ID
import com.sopt.now.presentation.util.KeyStorage.USER_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.USER_PW

class SignActivity : BindingActivity<ActivitySignBinding>(R.layout.activity_sign) {
    override fun initView() {
        initSignBtnClickListener()
    }

    private fun checkSignValid() {
        with(binding) {
            when {
                etSignId.text.trim().length !in MIN_ID_LEN..MAX_ID_LEN -> snackBar(root) {
                    getString(
                        R.string.error_message_sign_invalid_id
                    )
                }

                etSignPw.text.trim().length !in MIN_PW_LEN..MAX_PW_LEN -> snackBar(root) {
                    getString(
                        R.string.error_message_sign_invalid_pw
                    )
                }

                etSignNickname.text.trim()
                    .isEmpty() -> snackBar(root) { getString(R.string.error_message_sign_invalid_nickname) }

                etSignAge.text.trim().length !in MIN_AGE_LEN..MAX_AGE_LEN -> snackBar(root) {
                    getString(
                        R.string.error_message_sign_invalid_age
                    )
                }

                else -> navigateToLogin(
                    etSignId.text.toString(),
                    etSignPw.text.toString(),
                    etSignNickname.text.toString(),
                    etSignAge.text.toString()
                )
            }
        }
    }

    private fun navigateToLogin(id: String, pw: String, nickname: String, age: String) {
        val intent = Intent(this@SignActivity, LoginActivity::class.java).apply {
            putExtras(
                bundleOf(
                    USER_ID to id,
                    USER_PW to pw,
                    USER_NICKNAME to nickname,
                    USER_AGE to age
                )
            )
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initSignBtnClickListener() {
        binding.btnSignSign.setOnClickListener {
            checkSignValid()
        }
    }

    companion object {
        const val MIN_ID_LEN = 6
        const val MAX_ID_LEN = 10
        const val MIN_PW_LEN = 8
        const val MAX_PW_LEN = 12
        const val MIN_AGE_LEN = 1
        const val MAX_AGE_LEN = 3
    }
}