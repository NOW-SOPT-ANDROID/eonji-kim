package com.sopt.now.presentation.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.presentation.home.friend.FriendAdapter
import com.sopt.now.presentation.home.friend.FriendItemDecorator
import com.sopt.now.presentation.home.user.UserAdapter

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun initView() {
        initHomeAdapter()
    }

    private fun initHomeAdapter() {
        val userAdapter = UserAdapter().apply { submitList(homeViewModel.mockUserList) }
        val friendAdapter = FriendAdapter().apply { submitList(homeViewModel.mockFriendList) }

        binding.rvHomeFriends.run {
            adapter = ConcatAdapter(userAdapter, friendAdapter)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(FriendItemDecorator(context))
        }
    }
}
