package com.example.demo.views.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(
    private val leftSpacing: Float,
    private val rightSpacing: Float,
    private val topSpacing: Float,
    private val bottomSpacing: Float
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = rightSpacing.toInt()
        outRect.left = leftSpacing.toInt()
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = topSpacing.toInt()
        }
        outRect.bottom = bottomSpacing.toInt()
    }
}