package com.ft.my_document_organizer.data.model.forgot_password

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("data") val data: ForgotPasswordData? = ForgotPasswordData(),
    @SerializedName("http_code") val http_code: Int? = null,
    @SerializedName("type") val type: String? = null
)