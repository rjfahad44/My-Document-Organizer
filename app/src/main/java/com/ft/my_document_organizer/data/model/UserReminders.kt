package com.ft.my_document_organizer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_reminders")
data class UserReminders(
    var user_document_id: String,
    var event_id: String,
    var event_summary: String,
    var event_location: String,
    var event_description: String,
    var event_start: String,
    var event_end: String,
    var reminder_before_minutes: String,
    var event_timezone: String,
    var event_html_link: String,
    var sync_status : String,
    var created_at : String,
    var updated_at : String
)
{
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

//Title, location, Description, start_time, end_time, reminder_before_minutes, reminder_repeater, reminder_type (email, notification)
