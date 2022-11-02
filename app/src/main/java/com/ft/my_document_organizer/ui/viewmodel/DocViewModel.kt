package com.ft.my_document_organizer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ft.my_document_organizer.data.model.*
import com.ft.my_document_organizer.di.CoroutinesDispatcherProvider
import com.ft.my_document_organizer.network.repository.doc_repository.IDocRepository
import com.ft.my_document_organizer.state.NetworkState
import com.ft.my_document_organizer.utils.Constants
import com.ft.my_document_organizer.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DocViewModel @Inject constructor(
    private val docRepository: IDocRepository,
    private val networkHelper: NetworkHelper,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider) : ViewModel() {
/*
    *//*------------------------------RelationUserDocumentLabels------------------------------------*//*
    val allRelationUserDocumentLabels: LiveData<MutableList<RelationUserDocumentLabels>>
    *//*----------------------------RelationUserDocumentTypesCategories-----------------------------*//*
    val allRelationUserDocumentTypesCategories: LiveData<MutableList<RelationUserDocumentTypesCategories>>
    *//*------------------------------------TemplateCategories--------------------------------------*//*
    val allTemplateCategories: LiveData<MutableList<TemplateCategories>>
    *//*--------------------------------------TemplateInputs----------------------------------------*//*
    val allTemplateInputs: LiveData<MutableList<TemplateInputs>>
    *//*--------------------------------------UserCategories----------------------------------------*//*
    val allUserCategories: LiveData<MutableList<UserCategories>>
    *//*-------------------------------------UserDocumentFiles--------------------------------------*//*
    val allUserDocumentFiles: LiveData<MutableList<UserDocumentFiles>>
    *//*---------------------------------------UserDocuments----------------------------------------*//*
    val allUserDocuments: LiveData<MutableList<UserDocuments>>
    *//*-------------------------------------UserDocumentTypes--------------------------------------*//*
    val allUserDocumentTypes: LiveData<MutableList<UserDocumentTypes>>
    *//*-----------------------------------------UserLabels-----------------------------------------*//*
    val allUserLabels: LiveData<MutableList<UserLabels>>
    *//*-----------------------------------------UserNotes------------------------------------------*//*
    val allUserNotes: LiveData<MutableList<UserNotes>>
    *//*---------------------------------------UserReminders----------------------------------------*//*
    val allUserReminders: LiveData<MutableList<UserReminders>>*/

    /*init {

        allRelationUserDocumentLabels = docRepository.allRelationUserDocumentLabels
        allRelationUserDocumentTypesCategories = docRepository.allRelationUserDocumentTypesCategories
        allTemplateCategories = docRepository.allTemplateCategories
        allTemplateInputs = docRepository.allTemplateInputs
        allUserCategories = docRepository.allUserCategories
        allUserDocumentFiles = docRepository.allUserDocumentFiles
        allUserDocuments = docRepository.allUserDocuments
        allUserDocumentTypes = docRepository.allUserDocumentTypes
        allUserLabels = docRepository.allUserLabels
        allUserNotes = docRepository.allUserNotes
        allUserReminders = docRepository.allUserReminders
    }

    *//*------------------------------RelationUserDocumentLabels------------------------------------*//*
    fun insert(relationUserDocumentLabels: RelationUserDocumentLabels) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(relationUserDocumentLabels)
    }
    fun update(relationUserDocumentLabels: RelationUserDocumentLabels) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(relationUserDocumentLabels)
    }
    fun delete(relationUserDocumentLabels: RelationUserDocumentLabels) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(relationUserDocumentLabels)
    }


    *//*----------------------------RelationUserDocumentTypesCategories-----------------------------*//*
    fun insert(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(relationUserDocumentTypesCategories)
    }
    fun update(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(relationUserDocumentTypesCategories)
    }
    fun delete(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(relationUserDocumentTypesCategories)
    }

    *//*------------------------------------TemplateCategories--------------------------------------*//*
    fun insert(templateCategories: TemplateCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(templateCategories)
    }
    fun update(templateCategories: TemplateCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(templateCategories)
    }
    fun delete(templateCategories: TemplateCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(templateCategories)
    }


    *//*--------------------------------------TemplateInputs----------------------------------------*//*
    fun insert(templateInputs: TemplateInputs) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(templateInputs)
    }
    fun update(templateInputs: TemplateInputs) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(templateInputs)
    }
    fun delete(templateInputs: TemplateInputs) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(templateInputs)
    }


    *//*--------------------------------------UserCategories----------------------------------------*//*
    fun insert(userCategories: UserCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(userCategories)
    }
    fun update(userCategories: UserCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(userCategories)
    }
    fun delete(userCategories: UserCategories) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(userCategories)
    }


    *//*-------------------------------------UserDocumentFiles--------------------------------------*//*
    fun insert(userDocumentFiles: UserDocumentFiles) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(userDocumentFiles)
    }
    fun update(userDocumentFiles: UserDocumentFiles) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(userDocumentFiles)
    }
    fun delete(userDocumentFiles: UserDocumentFiles) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(userDocumentFiles)
    }


    *//*---------------------------------------UserDocuments----------------------------------------*//*
    fun insert(userDocuments: UserDocuments) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(userDocuments)
    }
    fun update(userDocuments: UserDocuments) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(userDocuments)
    }
    fun delete(userDocuments: UserDocuments) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(userDocuments)
    }


    *//*-------------------------------------UserDocumentTypes--------------------------------------*//*
    fun insert(userDocumentTypes: UserDocumentTypes) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(userDocumentTypes)
    }
    fun update(userDocumentTypes: UserDocumentTypes) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(userDocumentTypes)
    }
    fun delete(userDocumentTypes: UserDocumentTypes) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(userDocumentTypes)
    }


    *//*-----------------------------------------UserLabels-----------------------------------------*//*
    fun insert(userLabels: UserLabels) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(userLabels)
    }
    fun update(userLabels: UserLabels) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(userLabels)
    }
    fun delete(userLabels: UserLabels) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(userLabels)
    }


    *//*-----------------------------------------UserNotes------------------------------------------*//*
    fun insert(userNotes: UserNotes) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(userNotes)
    }
    fun update(userNotes: UserNotes) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(userNotes)
    }
    fun delete(userNotes: UserNotes) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(userNotes)
    }


    *//*---------------------------------------UserReminders----------------------------------------*//*
    fun insert(userReminders: UserReminders) = viewModelScope.launch(Dispatchers.IO){
        docRepository.insert(userReminders)
    }
    fun update(userReminders: UserReminders) = viewModelScope.launch(Dispatchers.IO){
        docRepository.update(userReminders)
    }
    fun delete(userReminders: UserReminders) = viewModelScope.launch(Dispatchers.IO){
        docRepository.delete(userReminders)
    }*/

    private val TAG = "DocViewModel"
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    private val _newsResponse = MutableStateFlow<NetworkState<NewsResponse>>(NetworkState.Empty())
    val newsResponse: StateFlow<NetworkState<NewsResponse>>
        get() = _newsResponse

    private val _searchNewsResponse =
        MutableStateFlow<NetworkState<NewsResponse>>(NetworkState.Empty())
    val searchNewsResponse: StateFlow<NetworkState<NewsResponse>>
        get() = _searchNewsResponse

    private var feedResponse: NewsResponse? = null
    var feedNewsPage = 1

    var searchEnable: Boolean = false
    var searchNewsPage = 1
    var searchResponse: NewsResponse? = null
    private var oldQuery: String = ""
    var newQuery: String = ""
    var totalPage = 1

    init {
        fetchNews(Constants.CountryCode)
    }

    fun fetchNews(countryCode: String) {
        if (feedNewsPage <= totalPage) {
            if (networkHelper.isNetworkConnected()) {
                viewModelScope.launch(coroutinesDispatcherProvider.io) {
                    _newsResponse.value = NetworkState.Loading()
                    when (val response = docRepository.getNews(countryCode, feedNewsPage)) {
                        is NetworkState.Success -> {
                            _newsResponse.value = handleFeedNewsResponse(response)
                            Timber.d(TAG, _newsResponse.value)
                        }
                        is NetworkState.Error -> {
                            _newsResponse.value =
                                NetworkState.Error(
                                    response.message ?: "Error"
                                )
                        }
                        else -> {}
                    }

                }
            } else {
                _errorMessage.value = "No internet available"
            }
        }
    }

    private fun handleFeedNewsResponse(response: NetworkState<NewsResponse>): NetworkState<NewsResponse> {
        response.data?.let { resultResponse ->
            if (feedResponse == null) {
                feedNewsPage = 2
                feedResponse = resultResponse
            } else {
                feedNewsPage++
                val oldArticles = feedResponse?.articles
                val newArticles = resultResponse.articles
                oldArticles?.addAll(newArticles)
            }
            //Conversion
            feedResponse?.let {
                convertPublishedDate(it)
            }
            return NetworkState.Success(feedResponse ?: resultResponse)
        }
        return NetworkState.Error("No data found")
    }

    fun searchNews(query: String) {
        newQuery = query
        if (newQuery.isNotEmpty() && searchNewsPage <= totalPage) {
            if (networkHelper.isNetworkConnected()) {
                viewModelScope.launch(coroutinesDispatcherProvider.io) {
                    _searchNewsResponse.value = NetworkState.Loading()
                    when (val response = docRepository.searchNews(query, searchNewsPage)) {
                        is NetworkState.Success -> {
                            _searchNewsResponse.value = handleSearchNewsResponse(response)
                        }
                        is NetworkState.Error -> {
                            _searchNewsResponse.value =
                                NetworkState.Error(
                                    response.message ?: "Error"
                                )
                        }
                        else -> {}
                    }
                }
            } else {
                _errorMessage.value = "No internet available"
            }
        }
    }

    private fun handleSearchNewsResponse(response: NetworkState<NewsResponse>): NetworkState<NewsResponse> {
        response.data?.let { resultResponse ->
            if (searchResponse == null || oldQuery != newQuery) {
                searchNewsPage = 2
                searchResponse = resultResponse
                oldQuery = newQuery
            } else {
                searchNewsPage++
                val oldArticles = searchResponse?.articles
                val newArticles = resultResponse.articles
                oldArticles?.addAll(newArticles)
            }
            searchResponse?.let {
                convertPublishedDate(it)
            }
            return NetworkState.Success(searchResponse ?: resultResponse)
        }
        return NetworkState.Error("No data found")
    }

    fun convertPublishedDate(currentResponse: NewsResponse) {
        currentResponse.articles.map { article ->
            article.publishedAt?.let {
                article.publishedAt = formatDate(it)
            }
        }
    }

    fun formatDate(strCurrentDate: String): String {
        var convertedDate = ""
        try {
            if (strCurrentDate.isNotEmpty() && strCurrentDate.contains("T")) {
                val local = Locale("US")
                var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", local)
                val newDate: Date? = format.parse(strCurrentDate)

                format = SimpleDateFormat("MMM dd, yyyy hh:mm a", local)
                newDate?.let {
                    convertedDate = format.format(it)
                }
            } else {
                convertedDate = strCurrentDate
            }
        } catch (e: Exception) {
            e.message?.let {
                Log.e(TAG, it)
            }
            convertedDate = strCurrentDate
        }
        return convertedDate
    }

    fun hideErrorToast() {
        _errorMessage.value = ""
    }

    fun saveNews(news: NewsArticle) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            onError(exception)
        }
        viewModelScope.launch(coroutinesDispatcherProvider.io + coroutineExceptionHandler) {
            docRepository.saveNews(news)
        }
    }

    fun getFavoriteNews() = docRepository.getSavedNews()

    fun deleteNews(news: NewsArticle) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            onError(exception)
        }
        viewModelScope.launch(coroutinesDispatcherProvider.io + coroutineExceptionHandler) {
            docRepository.deleteNews(news)
        }
    }

    fun clearSearch() {
        searchEnable = false
        searchResponse = null
        feedResponse = null
        feedNewsPage = 1
        searchNewsPage = 1
    }

    fun enableSearch() {
        searchEnable = true
    }

    private fun onError(throwable: Throwable) {
        throwable.message?.let {
            _errorMessage.value = it
        }
    }

}