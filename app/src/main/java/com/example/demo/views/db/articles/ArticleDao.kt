package com.example.demo.views.db.articles

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.demo.views.models.Articles

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles_table")
    fun getArticles(): LiveData<List<Articles>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Articles):Long

    @Delete
    suspend fun deleteArticle(article: Articles)
}
