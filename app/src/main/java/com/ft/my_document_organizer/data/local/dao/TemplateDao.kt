package com.ft.my_document_organizer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ft.my_document_organizer.data.model.TemplateCategories
import com.ft.my_document_organizer.data.model.TemplateInputs

@Dao
interface TemplateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(templateInputs: TemplateInputs)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(templateCategories: TemplateCategories)
}