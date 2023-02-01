package com.example.demo.views.repositories

import com.example.demo.views.models.Articles
import com.example.demo.views.models.Root
import com.example.demo.views.models.Source
import org.junit.Assert.*

class FakeFeedRepoTest{
     private val source = Source("1","tex")
     private val article1 = Articles(source,"title",false,"www.h.com","description",
          "url","publishedat","contents")
     private val article2 = Articles(source,"title",false,"www.h.com","description",
          "url","publishedat","contents")
     private val article3 = Articles(source,"title",false,"www.h.com","description",
          "url","publishedat","contents")
     private val article4 = Articles(source,"title",false,"www.h.com","description",
          "url","publishedat","contents")
     private val article5 = Articles(source,"title",false,"www.h.com","description",
          "url","publishedat","contents")
     private val article6 = Articles(source,"title",false,"www.h.com","description",
          "url","publishedat","contents")
     private val articles = listOf(article1,article2,article3,article4)

     private var shouldReturnNetworkError = false

     private val root : Root = Root("ok",articles)

     suspend fun getNewsFeed() = root

     fun  setShouldReturnNetworkError(value: Boolean){
          shouldReturnNetworkError = value
     }


}