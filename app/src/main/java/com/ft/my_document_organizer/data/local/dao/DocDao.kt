package com.ft.my_document_organizer.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ft.my_document_organizer.data.model.*

@Dao
interface DocDao {

    /*------------------------------RelationUserDocumentLabels------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(relationUserDocumentLabels: RelationUserDocumentLabels)

    @Update
    suspend fun update(relationUserDocumentLabels: RelationUserDocumentLabels)

    @Delete
    suspend fun delete(relationUserDocumentLabels: RelationUserDocumentLabels)

    @Query("DELETE FROM relation_user_document_labels")
    fun deleteAllRelationUserDocumentLabels()

    @Query("SELECT * FROM relation_user_document_labels")
    fun getAllRelationUserDocumentLabels(): LiveData<MutableList<RelationUserDocumentLabels>>


    /*----------------------------RelationUserDocumentTypesCategories-----------------------------*/
/*    @Transaction
    @Query("SELECT * FROM document_types_table")
    fun getDocAndField(): LiveData<MutableList<DocAndFieldCrossRef>>*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories)

    @Update
    suspend fun update(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories)

    @Delete
    suspend fun delete(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories)

    @Query("DELETE FROM relation_user_document_types_categories")
    fun deleteAllRelationUserDocumentTypesCategories()

    @Query("SELECT * FROM relation_user_document_types_categories")
    fun getAllRelationUserDocumentTypesCategories(): LiveData<MutableList<RelationUserDocumentTypesCategories>>


    /*------------------------------------TemplateCategories--------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(templateCategories: TemplateCategories)

    @Update
    suspend fun update(templateCategories: TemplateCategories)

    @Delete
    suspend fun delete(templateCategories: TemplateCategories)

    @Query("DELETE FROM template_categories")
    fun deleteAllTemplateCategories()

    @Query("SELECT * FROM template_categories")
    fun getAllTemplateCategories(): LiveData<MutableList<TemplateCategories>>

    @get:Query("SELECT * FROM template_categories")
    val all: List<TemplateCategories>

    /*--------------------------------------TemplateInputs----------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(templateInputs: TemplateInputs)

    @Update
    suspend fun update(templateInputs: TemplateInputs)

    @Delete
    suspend fun delete(templateInputs: TemplateInputs)

    @Query("DELETE FROM template_inputs")
    fun deleteAllTemplateInputs()

    @Query("SELECT * FROM template_inputs")
    fun getAllTemplateInputs(): LiveData<MutableList<TemplateInputs>>


    /*--------------------------------------UserCategories----------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userCategories: UserCategories)

    @Update
    suspend fun update(userCategories: UserCategories)

    @Delete
    suspend fun delete(userCategories: UserCategories)

    @Query("DELETE FROM user_categories")
    fun deleteAllUserCategories()

    @Query("SELECT * FROM user_categories")
    fun getAllUserCategories(): LiveData<MutableList<UserCategories>>


    /*-------------------------------------UserDocumentFiles--------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userDocumentFiles: UserDocumentFiles)

    @Update
    suspend fun update(userDocumentFiles: UserDocumentFiles)

    @Delete
    suspend fun delete(userDocumentFiles: UserDocumentFiles)

    @Query("DELETE FROM user_document_files")
    fun deleteAllUserDocumentFiles()

    @Query("SELECT * FROM user_document_files")
    fun getAllUserDocumentFiles(): LiveData<MutableList<UserDocumentFiles>>


    /*-------------------------------------UserDocuments--------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userDocuments: UserDocuments)

    @Update
    suspend fun update(userDocuments: UserDocuments)

    @Delete
    suspend fun delete(userDocuments: UserDocuments)

    @Query("DELETE FROM user_documents")
    fun deleteAllUserDocuments()

    @Query("SELECT * FROM user_documents")
    fun getAllUserDocuments(): LiveData<MutableList<UserDocuments>>


    /*-------------------------------------UserDocumentTypes--------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userDocumentTypes: UserDocumentTypes)

    @Update
    suspend fun update(userDocumentTypes: UserDocumentTypes)

    @Delete
    suspend fun delete(userDocumentTypes: UserDocumentTypes)

    @Query("DELETE FROM user_document_types")
    fun deleteAllUserDocumentTypes()

    @Query("SELECT * FROM user_document_types")
    fun getAllUserDocumentTypes(): LiveData<MutableList<UserDocumentTypes>>


    /*-----------------------------------------UserLabels-----------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userLabels: UserLabels)

    @Update
    suspend fun update(userLabels: UserLabels)

    @Delete
    suspend fun delete(userLabels: UserLabels)

    @Query("DELETE FROM user_labels")
    fun deleteAllUserLabels()

    @Query("SELECT * FROM user_labels")
    fun getAllUserLabels(): LiveData<MutableList<UserLabels>>


    /*-----------------------------------------UserNotes------------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userNotes: UserNotes)

    @Update
    suspend fun update(userNotes: UserNotes)

    @Delete
    suspend fun delete(userNotes: UserNotes)

    @Query("DELETE FROM user_notes")
    fun deleteAllUserNotes()

    @Query("SELECT * FROM user_notes")
    fun getAllUserNotes(): LiveData<MutableList<UserNotes>>


    /*---------------------------------------UserReminders----------------------------------------*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userReminders: UserReminders)

    @Update
    suspend fun update(userReminders: UserReminders)

    @Delete
    suspend fun delete(userReminders: UserReminders)

    @Query("DELETE FROM user_reminders")
    fun deleteAllUserReminders()

    @Query("SELECT * FROM user_reminders")
    fun getAllUserReminders(): LiveData<MutableList<UserReminders>>
    
}