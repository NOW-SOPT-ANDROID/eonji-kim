package com.sopt.now.presentation.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.presentation.home.friend.FriendAdapter
import com.sopt.now.presentation.home.friend.FriendItemDecorator

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun initView() {
        binding.rvHomeFriends.adapter = FriendAdapter().apply {
            submitList(homeViewModel.mockFriendList)
        }
        binding.rvHomeFriends.run {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(FriendItemDecorator(requireContext()))
        }
    }
}