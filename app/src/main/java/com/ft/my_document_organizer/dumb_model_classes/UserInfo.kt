package com.ft.my_document_organizer.dumb_model_classes

data class UserInfo(
    val email: String,
    val id: String,
    val name: String,
    val status: String,
    val user_category: UserCategory,
    val user_document_type: UserDocumentType
)