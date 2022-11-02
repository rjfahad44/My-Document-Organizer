package com.ft.my_document_organizer.data.model.otp.otp_verify

import com.google.gson.annotations.SerializedName

data class OtpVerifyResponse(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("data") val data: OtpVerifyData? = OtpVerifyData(),
    @SerializedName("http_code") val http_code: Int? = null,
    @SerializedName("type") val type: String? = null
)