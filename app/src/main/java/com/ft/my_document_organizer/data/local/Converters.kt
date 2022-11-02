package com.ft.my_document_organizer.data.local

import androidx.room.TypeConverter
import com.ft.my_document_organizer.data.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}