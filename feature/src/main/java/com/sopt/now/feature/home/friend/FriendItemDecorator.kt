package com.sopt.now.feature.home.friend

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.core_ui.util.context.pxToDp

class FriendItemDecorator(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = context.pxToDp(8)
        } else {
            outRect.bottom = context.pxToDp(2)
        }
        outRect.bottom = context.pxToDp(8)
    }
}