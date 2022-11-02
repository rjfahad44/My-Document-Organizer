package com.ft.my_document_organizer.data.model.login.login.google_signing

import com.google.gson.annotations.SerializedName

data class GoogleLoginUserInfo(
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null
)