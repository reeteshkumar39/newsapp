package com.example.demo.views.view.fragments.dashboard.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import com.example.demo.R
import com.example.demo.databinding.ItemDetailBinding
import com.example.demo.databinding.ItemMainNewsBinding
import com.example.demo.views.base.BaseViewHolder
import com.example.demo.views.base.ViewHolderBindingProperty
import com.example.demo.views.interfaces.DashBoardListener
import com.example.demo.views.view.fragments.dashboard.data.ListItemDetails
import com.example.demo.views.view.fragments.dashboard.data.ListItemMainNews
import com.example.demo.views.view.model.ListItems

class DetailsViewHolder(val view: View) :
    BaseViewHolder(view) {

    private val binding by ViewHolderBindingProperty(ItemDetailBinding::bind)
    private var articleClickListener : DashBoardListener? = null

    override fun bind(data: ListItems) {
        val article = (data as? ListItemDetails)?.articles
        binding.details = article
        binding.ivImage.setOnClickListener {
            article?.let { it1 -> articleClickListener?.onClickArticle(it1) }
        }
    }

    override fun setListener(listener: DashBoardListener) {
        this.articleClickListener = listener
    }

}