package com.ft.my_document_organizer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_notes")
data class UserNotes(
    var user_document_id: String,
    var note: String,
    var sync_status: String,
    var created_at : String,
    var updated_at : String
)
{
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
