package com.example.demo.views.view.fragments.dashboard

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.demo.views.base.BaseViewModel
import com.example.demo.views.db.history.HistoryEntity
import com.example.demo.views.models.Articles
import com.example.demo.views.models.Root
import com.example.demo.views.repositories.NewsFeedRepo
import com.example.demo.views.utils.default
import com.example.demo.views.utils.hideKeyboard
import com.example.demo.views.utils.showKeyBoard
import com.example.demo.views.view.fragments.dashboard.data.ListItemMainNews
import com.example.demo.views.view.fragments.dashboard.data.ListItemPopularNews
import com.example.demo.views.view.fragments.dashboard.data.ListItemTitle
import com.example.demo.views.view.model.ListItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel constructor(private val repository: NewsFeedRepo) : BaseViewModel() {
    private val TAG = "DashboardViewModel"
    private val dataList = ArrayList<ListItems>()

    val newsDataFeed: MutableLiveData<Root> by lazy {
        MutableLiveData<Root>()
    }

    val newsLists: MutableLiveData<ArrayList<ListItems>> by lazy {
        MutableLiveData<ArrayList<ListItems>>()
    }

    val searchBarVisible: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>().default(false)
    }

    fun setSearchBar(view: View, value: Boolean){
        if(value) view.context.showKeyBoard(view) else view.context.hideKeyboard(view)
        searchBarVisible.value = searchBarVisible.value != true
    }


    fun getNewsFeed() = viewModelScope.launch {
        eventShowLoading.value = true
        try{
            val newsFeed = repository.getNewsFeed()
            Log.d(TAG,newsFeed.body().toString())
            if (newsFeed.isSuccessful && newsFeed.body()?.status == "ok"){
                this@DashboardViewModel.newsDataFeed.value = newsFeed.body()
            }
        }catch (e: Exception){
            eventShowMessage.value = e.message
    }
    eventShowLoading.value = false
    }

    fun getViewListFromNewsFeed(articles: List<Articles>) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(articles.isNotEmpty()){
                    dataList.clear()
                    dataList.add(ListItemTitle("Top News"))
                    dataList.add(ListItemMainNews(articles[0]))
                    dataList.add(ListItemTitle("Popular News"))
                    articles.forEachIndexed{index, element ->
                        if(index != 0) dataList.add(ListItemPopularNews(element))
                    }
                }
                newsLists.postValue(dataList)

            }
        }

    fun saveArticle(article: Articles) = viewModelScope.launch {
        repository.upsertArticle(article)
    }

    fun getSavedNews() = repository.getSavedNews()

    fun deleteArticle(article: Articles) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun saveHistory(history: String) = viewModelScope.launch {
        val historyEntity = HistoryEntity(value = history)
       repository.upsertHistory(historyEntity)
    }

    fun getHistory() = repository.getHistory()

}