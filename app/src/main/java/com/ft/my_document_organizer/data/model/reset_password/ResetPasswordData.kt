package com.ft.my_document_organizer.data.model.reset_password

import com.google.gson.annotations.SerializedName

data class ResetPasswordData(
    @SerializedName("message") val message: String? = null
)