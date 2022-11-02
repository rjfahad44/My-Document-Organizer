package com.ft.my_document_organizer.data.model.forgot_password

import com.google.gson.annotations.SerializedName

data class ForgotPasswordUserInfo(
    @SerializedName("email") val email: String? = null
)