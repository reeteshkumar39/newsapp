package com.example.demo.views.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.demo.getOrAwaitValue
import com.example.demo.views.db.articles.ArticleDao
import com.example.demo.views.db.history.HistoryDao
import com.example.demo.views.db.history.HistoryEntity
import com.example.demo.views.models.Articles
import com.example.demo.views.models.Source
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class ArrticleRoomDatabaseTest: TestCase(){

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db : ArrticleRoomDatabase
    private lateinit var dao: ArticleDao
    private lateinit var historyDao: HistoryDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ArrticleRoomDatabase::class.java).allowMainThreadQueries().build()
        dao = db.getDao()
        historyDao = db.getHistoryDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun writeAndReadArticle() = runBlockingTest{
        val source = Source("1","tex")
        val article = Articles(source,"title",false,"www.h.com","description",
        "url","publishedat","contents")
        dao.upsert(article)
        val articles = dao.getArticles().getOrAwaitValue()
        assertThat(articles.contains(article)).isTrue()

    }

    @Test
    fun writeAndReadHistory() = runBlockingTest {
        val history = HistoryEntity(1,value = "hello")
        historyDao.upsert(history)
        val histories = historyDao.getHistory().getOrAwaitValue()
        assertThat(histories.contains(history)).isTrue()
    }

    @Test
    fun deleteArticle() = runBlockingTest {
        val source = Source("1","tex")
        val article = Articles(source,"title",false,"www.h.com","description",
            "url","publishedat","contents")
        dao.upsert(article)
        dao.deleteArticle(article)
        val articles = dao.getArticles().getOrAwaitValue()
        assertThat(articles).doesNotContain(article)
    }

}