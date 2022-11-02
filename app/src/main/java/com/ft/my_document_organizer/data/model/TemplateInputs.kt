package com.ft.my_document_organizer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "template_inputs")
data class TemplateInputs(
    var type: String,
    var created_at : String,
    var updated_at : String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
