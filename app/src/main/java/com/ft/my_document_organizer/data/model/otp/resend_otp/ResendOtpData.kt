package com.ft.my_document_organizer.data.model.otp.resend_otp

import com.google.gson.annotations.SerializedName

data class ResendOtpData(
    @SerializedName("access_token") val access_token: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("token_type") val token_type: String? = null,
    @SerializedName("user_info") val user_info: ResendOtpInfo? = ResendOtpInfo()
)