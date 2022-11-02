package com.ft.my_document_organizer.network.repository.doc_repository

import androidx.lifecycle.LiveData
import com.ft.my_document_organizer.data.model.NewsArticle
import com.ft.my_document_organizer.data.model.NewsResponse
import com.ft.my_document_organizer.state.NetworkState

interface IDocRepository {
    suspend fun getNews(countryCode: String, pageNumber: Int): NetworkState<NewsResponse>

    suspend fun searchNews(searchQuery: String, pageNumber: Int): NetworkState<NewsResponse>

    suspend fun saveNews(news: NewsArticle): Long

    fun getSavedNews(): LiveData<List<NewsArticle>>

    suspend fun deleteNews(news: NewsArticle)

    suspend fun deleteAllNews()
}
