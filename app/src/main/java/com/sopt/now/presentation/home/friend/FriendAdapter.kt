package com.sopt.now.presentation.home.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sopt.now.core.util.view.ItemDiffCallback
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.presentation.model.Friend

class FriendAdapter() : ListAdapter<Friend, FriendViewHolder>(FriendAdapterDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendViewHolder {
        val binding =
            ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FriendViewHolder,
        position: Int
    ) {
        holder.onBind(currentList[position])
    }

    companion object {
        private val FriendAdapterDiffCallback =
            ItemDiffCallback<Friend>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}