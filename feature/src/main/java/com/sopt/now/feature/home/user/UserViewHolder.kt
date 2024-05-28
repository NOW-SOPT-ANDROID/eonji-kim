package com.sopt.now.feature.home.user

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.feature.databinding.ItemUserBinding

class UserViewHolder(private val binding: ItemUserBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(userData: UserEntity) {
        binding.run {
            tvUserName.text = userData.nickname
            tvUserSelfDescription.text = userData.tel
        }
    }
}