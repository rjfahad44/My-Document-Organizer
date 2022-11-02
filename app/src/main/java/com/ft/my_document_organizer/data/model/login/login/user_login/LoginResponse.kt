package com.ft.my_document_organizer.data.model.login.login.user_login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val access_token: String? = null,
    @SerializedName("code") val code: Int? = null,
    @SerializedName("http_code") val http_code: Int? = null,
    @SerializedName("data") val data: LoginData? = LoginData(),
    @SerializedName("token_type") val token_type: String? = null,
    @SerializedName("type") val type: String? = null
)