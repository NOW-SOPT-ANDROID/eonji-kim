package com.sopt.now.presentation.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.util.fragment.snackBar
import com.sopt.now.core.util.view.UiState
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.presentation.home.friend.FriendAdapter
import com.sopt.now.presentation.home.friend.FriendItemDecorator
import com.sopt.now.presentation.home.user.UserAdapter
import com.sopt.now.presentation.home.friend.Friend
import timber.log.Timber

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun initView() {
        initObserverUserList()
    }

    private fun initObserverUserList() {
        homeViewModel.getUserList.observe(this) {
            when (it) {
                is UiState.Success -> initHomeAdapter(it.data)
                is UiState.Failure -> initErrorMessage(it.errorMessage)
                is UiState.Loading -> Timber.d("로딩중")
            }
        }
    }

    private fun initErrorMessage(errorMessage: String) {
        snackBar(binding.root) { "유저 리스트 조회 실패 : $errorMessage" }
    }

    private fun initHomeAdapter(friendList: List<Friend>?) {
        val userAdapter = UserAdapter().apply { submitList(homeViewModel.mockUserList) }
        val friendAdapter = FriendAdapter().apply { submitList(friendList) }

        binding.rvHomeFriends.run {
            adapter = ConcatAdapter(userAdapter, friendAdapter)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(FriendItemDecorator(context))
        }
    }
}
