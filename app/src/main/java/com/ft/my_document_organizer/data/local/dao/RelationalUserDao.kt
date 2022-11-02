package com.ft.my_document_organizer.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ft.my_document_organizer.data.model.RelationUserDocumentLabels
import com.ft.my_document_organizer.data.model.RelationUserDocumentTypesCategories

@Dao
interface RelationalUserDao {

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
}