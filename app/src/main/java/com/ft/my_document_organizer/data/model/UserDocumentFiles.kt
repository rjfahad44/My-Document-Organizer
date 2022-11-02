package com.ft.my_document_organizer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_document_files")
data class UserDocumentFiles(
    var user_document_idIndex: String,
    var file_name: String,
    var file_size: String,
    var file_mime_type: String,
    var created_at : String,
    var updated_at : String
)
{
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}