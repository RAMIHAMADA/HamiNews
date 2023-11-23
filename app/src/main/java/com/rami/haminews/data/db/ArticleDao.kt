package com.rami.haminews.data.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rami.haminews.models.Article

interface ArticleDao {

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): LiveData<List<Article>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert()

    @Delete
    suspend fun delete(article: Article)
}
