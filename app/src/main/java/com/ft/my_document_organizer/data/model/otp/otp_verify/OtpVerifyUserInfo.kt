package com.ft.my_document_organizer.data.model.otp.otp_verify

import com.google.gson.annotations.SerializedName

data class OtpVerifyUserInfo(
    @SerializedName("code") val code: String? = null
)