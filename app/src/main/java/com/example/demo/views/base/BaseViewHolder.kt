package com.example.demo.views.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.views.interfaces.DashBoardListener
import com.example.demo.views.view.model.ListItems

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: ListItems)
    abstract fun setListener(listener: DashBoardListener)
}