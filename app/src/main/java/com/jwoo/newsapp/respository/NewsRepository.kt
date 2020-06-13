package com.jwoo.newsapp.respository

import com.jwoo.newsapp.api.RetrofitInstance
import com.jwoo.newsapp.db.ArticleDatabase

class NewsRepository(val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}