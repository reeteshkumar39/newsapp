package com.example.demo.views.view.fragments.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.demo.views.base.BaseViewModel
import com.example.demo.views.models.Articles
import com.example.demo.views.models.Root
import com.example.demo.views.view.fragments.dashboard.data.ListItemDetails
import com.example.demo.views.view.fragments.dashboard.data.ListItemPopularNews
import com.example.demo.views.view.fragments.dashboard.data.ListItemTitle
import com.example.demo.views.view.model.ListItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel : BaseViewModel() {

    val newsLists: MutableLiveData<ArrayList<ListItems>> by lazy {
        MutableLiveData<ArrayList<ListItems>>()
    }
    private val dataList = ArrayList<ListItems>()

    fun getViewListFromNewsFeed(root: Root, articles: Articles) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(root.articles?.isNotEmpty() == true){
                    dataList.clear()
                    dataList.add(ListItemDetails(articles))
                    dataList.add(ListItemTitle("Popular News"))
                    root.articles.forEach{
                        dataList.add(ListItemPopularNews(it))
                    }
                }
                newsLists.postValue(dataList)

            }
        }
}