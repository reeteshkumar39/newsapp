package com.example.demo.views.interfaces

import com.example.demo.views.models.Articles

interface DashBoardListener {
    fun onClickBookmark(article: Articles)
    fun onClickArticle(article: Articles)
}