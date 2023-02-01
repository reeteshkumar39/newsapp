package com.example.demo.views.view.fragments.dashboard.data


import com.example.demo.views.models.Articles
import com.example.demo.views.view.model.ListItems

data class ListItemMainNews(
    val article: Articles
) : ListItems

data class ListItemPopularNews(
    val articles: Articles
) : ListItems


data class ListItemDetails(
    val articles: Articles
) : ListItems

data class ListItemTitle(
    val title: String?
) : ListItems



