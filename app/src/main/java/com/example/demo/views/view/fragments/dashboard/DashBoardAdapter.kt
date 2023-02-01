package com.example.demo.views.view.fragments.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.views.base.BaseViewHolder
import com.example.demo.views.interfaces.DashBoardListener
import com.example.demo.views.view.fragments.dashboard.data.ListItemDetails
import com.example.demo.views.view.fragments.dashboard.data.ListItemMainNews
import com.example.demo.views.view.fragments.dashboard.data.ListItemPopularNews
import com.example.demo.views.view.fragments.dashboard.data.ListItemTitle
import com.example.demo.views.view.fragments.dashboard.viewholders.DetailsViewHolder
import com.example.demo.views.view.fragments.dashboard.viewholders.MainNewsViewHolder
import com.example.demo.views.view.fragments.dashboard.viewholders.PopularNewsViewHolder
import com.example.demo.views.view.fragments.dashboard.viewholders.TitleViewHolder
import com.example.demo.views.view.model.ListItems

class DashBoardAdapter() :
    RecyclerView.Adapter<BaseViewHolder>() {

    var data: List<ListItems> = emptyList()
    var listener: DashBoardListener? = null

    private val viewHolders = mapOf<Int, ((itemView: View) -> BaseViewHolder)>(
        R.layout.item_title to ::TitleViewHolder,
        R.layout.item_main_news to ::MainNewsViewHolder,
        R.layout.item_popular_news to ::PopularNewsViewHolder,
        R.layout.item_detail to ::DetailsViewHolder
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return viewHolders.getValue(viewType)
            .invoke(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if(listener != null)
            holder.setListener(listener!!)
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = when (data[position]) {
        is ListItemTitle -> R.layout.item_title
        is ListItemMainNews -> R.layout.item_main_news
        is ListItemPopularNews -> R.layout.item_popular_news
        is ListItemDetails -> R.layout.item_detail
        else -> R.layout.item_bottom_pager
    }
}