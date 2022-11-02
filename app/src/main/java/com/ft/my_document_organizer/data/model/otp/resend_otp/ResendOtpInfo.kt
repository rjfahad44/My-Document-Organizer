package com.ft.my_document_organizer.data.model.otp.resend_otp

import com.google.gson.annotations.SerializedName

data class ResendOtpInfo(
    @SerializedName("message") val message: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("email") val email: String? = null
)