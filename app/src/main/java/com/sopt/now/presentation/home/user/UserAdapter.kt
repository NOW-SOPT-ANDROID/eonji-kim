package com.sopt.now.presentation.home.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sopt.now.core.util.view.ItemDiffCallback
import com.sopt.now.databinding.ItemUserBinding

class UserAdapter() : ListAdapter<User, UserViewHolder>(UserAdapterDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        holder.onBind(currentList[position])
    }

    companion object {
        private val UserAdapterDiffCallback =
            ItemDiffCallback<User>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}