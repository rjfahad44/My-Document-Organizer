package com.ft.my_document_organizer.dumb_model_classes

data class Test(
    val access_token: String,
    val code: Int,
    val `data`: Data,
    val http_code: Int,
    val token_type: String,
    val type: String
)