package com.sopt.now.feature.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.core_ui.base.BindingFragment
import com.sopt.now.core_ui.util.fragment.snackBar
import com.sopt.now.core_ui.util.fragment.viewLifeCycle
import com.sopt.now.core_ui.util.fragment.viewLifeCycleScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.entity.FriendEntity
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.FragmentHomeBinding
import com.sopt.now.feature.home.friend.FriendAdapter
import com.sopt.now.feature.home.friend.FriendItemDecorator
import com.sopt.now.feature.home.user.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun initView() {
        initObserverUserList()
    }

    private fun initObserverUserList() {
        homeViewModel.getUserList.flowWithLifecycle(viewLifeCycle).onEach {
            when (it) {
                is UiState.Success -> initHomeAdapter(it.data)
                is UiState.Failure -> initErrorMessage(it.errorMessage)
                is UiState.Loading -> Timber.d("로딩중")
                is UiState.Empty -> Timber.d("empty")
            }
        }.launchIn(viewLifeCycleScope)
    }

    private fun initErrorMessage(errorMessage: String) {
        snackBar(binding.root) { "유저 리스트 조회 실패 : $errorMessage" }
    }

    private fun initHomeAdapter(friendList: List<FriendEntity>) {
        val userAdapter = UserAdapter().apply { submitList(homeViewModel.mockUserList) }
        val friendAdapter = FriendAdapter().apply { submitList(friendList) }

        binding.rvHomeFriends.run {
            adapter = ConcatAdapter(userAdapter, friendAdapter)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(FriendItemDecorator(context))
        }
    }
}
