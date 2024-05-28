package com.sopt.now.feature.home.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sopt.now.core_ui.view.ItemDiffCallback
import com.sopt.now.domain.entity.FriendEntity
import com.sopt.now.feature.databinding.ItemFriendBinding

class FriendAdapter() : ListAdapter<FriendEntity, FriendViewHolder>(FriendAdapterDiffCallback) {
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
            ItemDiffCallback<FriendEntity>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}