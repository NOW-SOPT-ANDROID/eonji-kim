package com.sopt.now.feature.home.friend

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sopt.now.domain.entity.FriendEntity
import com.sopt.now.feature.databinding.ItemFriendBinding

class FriendViewHolder(private val binding: ItemFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(friendData: FriendEntity) {
        binding.run {
            Glide.with(root.context).load(friendData.avatar).into(ivFriendProfile)
            tvFriendName.text = "${friendData.lastName}\n${friendData.firstName}"
            tvFriendSelfDescription.text = friendData.email
        }
    }
}