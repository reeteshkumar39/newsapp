package com.example.demo.views.view.fragments.dashboard.viewholders

import android.view.View
import com.example.demo.databinding.ItemTitleBinding
import com.example.demo.views.base.BaseViewHolder
import com.example.demo.views.base.ViewHolderBindingProperty
import com.example.demo.views.interfaces.DashBoardListener
import com.example.demo.views.view.fragments.dashboard.data.ListItemTitle
import com.example.demo.views.view.model.ListItems

class TitleViewHolder(val view: View) :
    BaseViewHolder(view) {

    private val binding by ViewHolderBindingProperty(ItemTitleBinding::bind)
    private var bigImageClickListener : DashBoardListener? = null

    override fun bind(data: ListItems) {
        binding.title1 = (data as? ListItemTitle)?.title
    }

    override fun setListener(listener: DashBoardListener) {
        this.bigImageClickListener = listener
    }

}