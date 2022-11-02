package com.ft.my_document_organizer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "relation_user_document_types_categories")
data class RelationUserDocumentTypesCategories(
    var user_category_id: String,
    var user_document_type_id: String,
    var created_at : String,
    var updated_at : String
)
{
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
