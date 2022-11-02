package com.ft.my_document_organizer.data.model.change_password

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("data") val data: ChangePasswordData? = ChangePasswordData(),
    @SerializedName("http_code") val http_code: Int? = null,
    @SerializedName("type") val type: String? = null
)