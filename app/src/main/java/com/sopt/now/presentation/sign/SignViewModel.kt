package com.sopt.now.presentation.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.data.ServicePool
import com.sopt.now.data.dto.request.RequestSignDto
import com.sopt.now.presentation.home.user.User
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_ID
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_NICKNAME
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_PW
import com.sopt.now.presentation.util.KeyStorage.ERROR_SIGN_TEL
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignViewModel : ViewModel() {
    private val _postSign = MutableLiveData<SignState>()
    val postSign: MutableLiveData<SignState> = _postSign

    fun postSign(user: User) = viewModelScope.launch {
        if (checkSignValid(user) == null) {
            runCatching {
                ServicePool.authServiceApi.postSign(
                    RequestSignDto(user.id, user.pw, user.nickname, user.tel)
                )
            }.fold(
                {
                    if (it.code() == 201) {
                        _postSign.value =
                            SignState(
                                true,
                                "회원가입 성공! 유저의 ID는 ${it.headers()["location"]} 입니둥"
                            ).apply {
//                                MainApplication.prefs.setUser(
//                                    User(user.id, user.pw, user.nickname, user.tel)
//                                )
                            }
                    } else _postSign.value =
                        SignState(false, "${it.errorBody()?.string()?.split("\"")?.get(5)}")
                },
                { _postSign.value = SignState(false, "${it.message}") }
            )
        } else {
            _postSign.value = checkSignValid(user)
        }
    }

    private fun checkSignValid(user: User): SignState? {
        return when {
            !getSignIdValid(user.id) -> SignState(false, ERROR_SIGN_ID)
            !getSignPwValid(user.pw) -> SignState(false, ERROR_SIGN_PW)
            !getSignNicknameValid(user.nickname) -> SignState(false, ERROR_SIGN_NICKNAME)
            !getSignTelValid(user.tel) -> SignState(false, ERROR_SIGN_TEL)
            else -> null
        }
    }

    private fun getSignIdValid(userID: String) = userID.length in MIN_ID_LEN..MAX_ID_LEN
    private fun getSignPwValid(userPw: String): Boolean {
        Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+\$).{8,}\$").let {
            return it.matcher(userPw).matches()
        }
    }

    private fun getSignNicknameValid(userNickname: String) = userNickname.isNotBlank()
    private fun getSignTelValid(userTel: String): Boolean {
        Pattern.compile("^010-\\d{4}-\\d{4}\$").let {
            return it.matcher(userTel).matches()
        }
    }

    companion object {
        const val MIN_ID_LEN = 6
        const val MAX_ID_LEN = 10
    }
}