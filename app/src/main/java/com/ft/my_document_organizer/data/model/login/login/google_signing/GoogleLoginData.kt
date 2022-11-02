package com.ft.my_document_organizer.data.model.login.login.google_signing

import com.google.gson.annotations.SerializedName

data class GoogleLoginData(
    @SerializedName("access_token") val access_token: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("token_type") val token_type: String? = null,
    @SerializedName("user_info") val user_info: GoogleLoginUserInfo? = GoogleLoginUserInfo(),

    )