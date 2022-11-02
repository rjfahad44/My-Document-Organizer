package com.ft.my_document_organizer.data.model.forgot_password

import com.google.gson.annotations.SerializedName

data class ForgotPasswordData(
    @SerializedName("access_token") val access_token: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("token_type") val token_type: String? = null,
    @SerializedName("user_info") val user_info: ForgotPasswordUserInfo? = ForgotPasswordUserInfo()
)