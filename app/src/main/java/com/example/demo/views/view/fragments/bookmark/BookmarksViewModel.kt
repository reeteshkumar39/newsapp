package com.example.demo.views.view.fragments.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.demo.views.base.BaseViewModel
import com.example.demo.views.models.Articles
import com.example.demo.views.models.Root
import com.example.demo.views.repositories.NewsFeedRepo
import com.example.demo.views.view.fragments.dashboard.data.ListItemDetails
import com.example.demo.views.view.fragments.dashboard.data.ListItemPopularNews
import com.example.demo.views.view.fragments.dashboard.data.ListItemTitle
import com.example.demo.views.view.model.ListItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarksViewModel constructor(private val repository: NewsFeedRepo): BaseViewModel() {

    val bookmarksList: MutableLiveData<ArrayList<ListItems>> by lazy {
        MutableLiveData<ArrayList<ListItems>>()
    }
    private val dataList = ArrayList<ListItems>()

    fun getBookmarksList(articles: List<Articles>) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(articles.isNotEmpty()){
                    dataList.clear()
                    articles.forEach{
                        dataList.add(ListItemPopularNews(it))
                    }
                }else{
                    dataList.clear()
                }
                bookmarksList.postValue(dataList)

            }
        }

    fun deleteArticle(article: Articles) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun getSavedNews() = repository.getSavedNews()

}