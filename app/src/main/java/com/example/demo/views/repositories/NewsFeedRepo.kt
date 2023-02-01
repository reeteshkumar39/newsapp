package com.example.demo.views.repositories

import com.example.demo.views.db.ArrticleRoomDatabase
import com.example.demo.views.db.history.HistoryEntity
import com.example.demo.views.models.Articles
import com.example.demo.views.models.Root
import com.example.demo.views.network.ApiInterface

class NewsFeedRepo constructor(private val apiInterface: ApiInterface,private val db: ArrticleRoomDatabase) {
    suspend fun getNewsFeed() = apiInterface.getNews()

    suspend fun upsertArticle(article: Articles) = db.getDao().upsert(article)

    fun getSavedNews() = db.getDao().getArticles()

    suspend fun deleteArticle(article: Articles) = db.getDao().deleteArticle(article)

    suspend fun upsertHistory(value: HistoryEntity) = db.getHistoryDao().upsert(value)

    fun getHistory() = db.getHistoryDao().getHistory()

}
