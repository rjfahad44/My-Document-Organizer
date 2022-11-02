package com.ft.my_document_organizer.data.model.login.login.google_signing

import com.google.gson.annotations.SerializedName

data class GoogleLoginResponse(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("data") val data: GoogleLoginData = GoogleLoginData(),
    @SerializedName("http_code") val http_code: Int? = null,
    @SerializedName("type") val type: String? = null
)