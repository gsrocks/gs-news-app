package com.gsrocks.gsnewsapp.feature.news.data.local

import androidx.room.*
import com.gsrocks.gsnewsapp.feature.news.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateArticle(articleEntity: ArticleEntity)

    @Delete
    suspend fun deleteArticle(articleEntity: ArticleEntity)

    @Query("SELECT * FROM articleentity")
    fun getFavouriteArticles(): Flow<List<ArticleEntity>>
}
