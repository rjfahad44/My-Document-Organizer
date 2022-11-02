package com.ft.my_document_organizer.network.repository.doc_repository

import androidx.lifecycle.LiveData
import com.ft.my_document_organizer.data.local.dao.DocDao
import com.ft.my_document_organizer.data.model.*
import com.ft.my_document_organizer.network.api.ApiHelper
import com.ft.my_document_organizer.state.NetworkState
import javax.inject.Inject

class DocRepository @Inject constructor(
    private val remoteDataSource: ApiHelper,
    private val docDao: DocDao
) : IDocRepository {

    val allRelationUserDocumentLabels: LiveData<MutableList<RelationUserDocumentLabels>> = docDao.getAllRelationUserDocumentLabels()
    val allRelationUserDocumentTypesCategories: LiveData<MutableList<RelationUserDocumentTypesCategories>> = docDao.getAllRelationUserDocumentTypesCategories()
    val allTemplateCategories: LiveData<MutableList<TemplateCategories>> = docDao.getAllTemplateCategories()
    val allTemplateInputs: LiveData<MutableList<TemplateInputs>> = docDao.getAllTemplateInputs()
    val allUserCategories: LiveData<MutableList<UserCategories>> = docDao.getAllUserCategories()
    val allUserDocumentFiles: LiveData<MutableList<UserDocumentFiles>> = docDao.getAllUserDocumentFiles()
    val allUserDocuments: LiveData<MutableList<UserDocuments>> = docDao.getAllUserDocuments()
    val allUserDocumentTypes: LiveData<MutableList<UserDocumentTypes>> = docDao.getAllUserDocumentTypes()
    val allUserLabels: LiveData<MutableList<UserLabels>> = docDao.getAllUserLabels()
    val allUserNotes: LiveData<MutableList<UserNotes>> = docDao.getAllUserNotes()
    val allUserReminders: LiveData<MutableList<UserReminders>> = docDao.getAllUserReminders()

    /*------------------------------RelationUserDocumentLabels------------------------------------*/
    suspend fun insert(relationUserDocumentLabels: RelationUserDocumentLabels) {
        docDao.insert(relationUserDocumentLabels)
    }

    suspend fun update(relationUserDocumentLabels: RelationUserDocumentLabels) {
        docDao.update(relationUserDocumentLabels)
    }

    suspend fun delete(relationUserDocumentLabels: RelationUserDocumentLabels) {
        docDao.delete(relationUserDocumentLabels)
    }

    /*----------------------------RelationUserDocumentTypesCategories-----------------------------*/
    suspend fun insert(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) {
        docDao.insert(relationUserDocumentTypesCategories)
    }

    suspend fun update(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) {
        docDao.update(relationUserDocumentTypesCategories)
    }

    suspend fun delete(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) {
        docDao.delete(relationUserDocumentTypesCategories)
    }


    /*------------------------------------TemplateCategories--------------------------------------*/
    suspend fun insert(templateCategories: TemplateCategories) {
        docDao.insert(templateCategories)
    }

    suspend fun update(templateCategories: TemplateCategories) {
        docDao.update(templateCategories)
    }

    suspend fun delete(templateCategories: TemplateCategories) {
        docDao.delete(templateCategories)
    }


    /*--------------------------------------TemplateInputs----------------------------------------*/
    suspend fun insert(templateInputs: TemplateInputs) {
        docDao.insert(templateInputs)
    }

    suspend fun update(templateInputs: TemplateInputs) {
        docDao.update(templateInputs)
    }

    suspend fun delete(templateInputs: TemplateInputs) {
        docDao.delete(templateInputs)
    }


    /*--------------------------------------UserCategories----------------------------------------*/
    suspend fun insert(userCategories: UserCategories) {
        docDao.insert(userCategories)
    }

    suspend fun update(userCategories: UserCategories) {
        docDao.update(userCategories)
    }

    suspend fun delete(userCategories: UserCategories) {
        docDao.delete(userCategories)
    }


    /*-------------------------------------UserDocumentFiles--------------------------------------*/
    suspend fun insert(userDocumentFiles: UserDocumentFiles) {
        docDao.insert(userDocumentFiles)
    }

    suspend fun update(userDocumentFiles: UserDocumentFiles) {
        docDao.update(userDocumentFiles)
    }

    suspend fun delete(userDocumentFiles: UserDocumentFiles) {
        docDao.delete(userDocumentFiles)
    }


    /*---------------------------------------UserDocuments----------------------------------------*/
    suspend fun insert(userDocuments: UserDocuments) {
        docDao.insert(userDocuments)
    }

    suspend fun update(userDocuments: UserDocuments) {
        docDao.update(userDocuments)
    }

    suspend fun delete(userDocuments: UserDocuments) {
        docDao.delete(userDocuments)
    }


    /*-------------------------------------UserDocumentTypes--------------------------------------*/
    suspend fun insert(userDocumentTypes: UserDocumentTypes) {
        docDao.insert(userDocumentTypes)
    }

    suspend fun update(userDocumentTypes: UserDocumentTypes) {
        docDao.update(userDocumentTypes)
    }

    suspend fun delete(userDocumentTypes: UserDocumentTypes) {
        docDao.delete(userDocumentTypes)
    }


    /*-----------------------------------------UserLabels-----------------------------------------*/
    suspend fun insert(userLabels: UserLabels) {
        docDao.insert(userLabels)
    }

    suspend fun update(userLabels: UserLabels) {
        docDao.update(userLabels)
    }

    suspend fun delete(userLabels: UserLabels) {
        docDao.delete(userLabels)
    }


    /*-----------------------------------------UserNotes------------------------------------------*/
    suspend fun insert(userNotes: UserNotes) {
        docDao.insert(userNotes)
    }

    suspend fun update(userNotes: UserNotes) {
        docDao.update(userNotes)
    }

    suspend fun delete(userNotes: UserNotes) {
        docDao.delete(userNotes)
    }


    /*---------------------------------------UserReminders----------------------------------------*/
    suspend fun insert(userReminders: UserReminders) {
        docDao.insert(userReminders)
    }

    suspend fun update(userReminders: UserReminders) {
        docDao.update(userReminders)
    }

    suspend fun delete(userReminders: UserReminders) {
        docDao.delete(userReminders)
    }

    override suspend fun getNews(
        countryCode: String,
        pageNumber: Int
    ): NetworkState<NewsResponse> {
        return try {
            val response = remoteDataSource.getNews(countryCode, pageNumber)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                NetworkState.Error("An error occurred")
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    override suspend fun searchNews(
        searchQuery: String,
        pageNumber: Int
    ): NetworkState<NewsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(news: NewsArticle): Long {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): LiveData<List<NewsArticle>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(news: NewsArticle) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllNews() {
        TODO("Not yet implemented")
    }
}