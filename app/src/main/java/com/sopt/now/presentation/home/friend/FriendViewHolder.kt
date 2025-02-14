package com.sopt.now.presentation.home.friend

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sopt.now.databinding.ItemFriendBinding

class FriendViewHolder(private val binding: ItemFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(friendData: Friend) {
        binding.run {
            Glide.with(root.context).load(friendData.avatar).into(ivFriendProfile)
            tvFriendName.text = "${friendData.lastName}\n${friendData.firstName}"
            tvFriendSelfDescription.text = friendData.email
        }
    }
}