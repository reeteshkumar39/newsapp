package com.example.demo.views.view.fragments.dashboard.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import com.example.demo.R
import com.example.demo.databinding.ItemPopularNewsBinding
import com.example.demo.views.base.BaseViewHolder
import com.example.demo.views.base.ViewHolderBindingProperty
import com.example.demo.views.interfaces.DashBoardListener
import com.example.demo.views.view.fragments.dashboard.data.ListItemPopularNews
import com.example.demo.views.view.model.ListItems

class PopularNewsViewHolder(val view: View) :
    BaseViewHolder(view) {

    private val binding by ViewHolderBindingProperty(ItemPopularNewsBinding::bind)
    private var popularNewsListener : DashBoardListener? = null

    override fun bind(data: ListItems) {
        val article = (data as? ListItemPopularNews)?.articles
        binding.popNews = article
        binding.ivImage.setOnClickListener {
            article?.let { it1 -> popularNewsListener?.onClickArticle(it1) }
        }
        binding.ivBookmark.setOnClickListener{
            article?.let { it1 -> popularNewsListener?.onClickBookmark(it1) }
        }
        if(article?.isBookmarked == true){
            binding.ivBookmark.setColorFilter(ContextCompat.getColor(view.context, R.color.purple_700))
        }else{
            binding.ivBookmark.setColorFilter(ContextCompat.getColor(view.context, R.color.clr_575757))

        }
    }

    override fun setListener(listener: DashBoardListener) {
        this.popularNewsListener = listener
    }

}