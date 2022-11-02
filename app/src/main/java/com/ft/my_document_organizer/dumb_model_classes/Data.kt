package com.ft.my_document_organizer.dumb_model_classes

data class Data(
    val access_token: String,
    val message: String,
    val token_type: String,
    val user_info: UserInfo
)