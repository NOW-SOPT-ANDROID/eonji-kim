package com.sopt.now.presentation.home.friend

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.presentation.model.Friend

class FriendViewHolder(private val binding: ItemFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(friendData: Friend) {
        binding.run {
            ivFriendProfile.setImageResource(friendData.profileImage)
            tvFriendName.text = friendData.name
            tvFriendSelfDescription.text = friendData.selfDescription
        }
    }
}