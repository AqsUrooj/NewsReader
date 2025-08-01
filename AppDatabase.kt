package com.example.newsreaderapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteArticle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteArticleDao(): FavoriteArticleDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "news_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
