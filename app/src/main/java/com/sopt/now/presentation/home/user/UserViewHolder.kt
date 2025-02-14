package com.sopt.now.presentation.home.user

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemUserBinding

class UserViewHolder(private val binding: ItemUserBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(userData: User) {
        binding.run {
            tvUserName.text = userData.nickname
            tvUserSelfDescription.text = userData.tel
        }
    }
}