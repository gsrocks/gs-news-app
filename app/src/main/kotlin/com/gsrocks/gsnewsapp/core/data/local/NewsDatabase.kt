package com.gsrocks.gsnewsapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gsrocks.gsnewsapp.feature.news.data.local.NewsDao
import com.gsrocks.gsnewsapp.feature.news.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao
}
