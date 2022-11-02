package com.ft.my_document_organizer.data.model.login.login.user_login

import com.google.gson.annotations.SerializedName
import com.ft.my_document_organizer.dumb_model_classes.UserCategory
import com.ft.my_document_organizer.dumb_model_classes.UserDocumentType

data class LoginUserInfo(
    @SerializedName("email") val email: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("user_category") val user_category: UserCategory? = null,
    @SerializedName("user_document_type") val user_document_type: UserDocumentType? = null
)