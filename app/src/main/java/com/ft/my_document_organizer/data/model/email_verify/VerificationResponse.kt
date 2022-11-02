package com.ft.my_document_organizer.data.model.email_verify

import com.google.gson.annotations.SerializedName

data class VerificationResponse(
    @SerializedName("code") val code: Int? = null,
    @SerializedName("http_code") val http_code: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("data") val data: VerificationData? = VerificationData()
)