package com.ft.my_document_organizer.data.model.reset_password

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("data") val data: ResetPasswordData? = ResetPasswordData(),
    @SerializedName("http_code") val http_code: Int? = null,
    @SerializedName("type") val type: String? = null
)