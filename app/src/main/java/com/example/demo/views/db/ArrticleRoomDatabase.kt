package com.example.demo.views.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demo.views.db.articles.ArticleDao
import com.example.demo.views.db.history.HistoryDao
import com.example.demo.views.db.history.HistoryEntity
import com.example.demo.views.models.Articles

@Database( entities = [Articles::class,HistoryEntity::class], version = 1, exportSchema = false)
abstract class ArrticleRoomDatabase : RoomDatabase() {

    abstract fun getDao(): ArticleDao
    abstract fun getHistoryDao(): HistoryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ArrticleRoomDatabase? = null

        fun getDatabase(context: Context): ArrticleRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArrticleRoomDatabase::class.java,
                    "article_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
