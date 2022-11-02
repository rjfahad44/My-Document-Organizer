package com.ft.my_document_organizer.data.model.otp.resend_otp

import com.google.gson.annotations.SerializedName

data class ResendOtpResponse(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("http_code") val http_code: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("data") var data: ResendOtpData? = ResendOtpData()
)