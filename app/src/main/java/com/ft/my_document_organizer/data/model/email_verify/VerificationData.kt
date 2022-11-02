package com.ft.my_document_organizer.data.model.email_verify

import com.google.gson.annotations.SerializedName

data class VerificationData(
    @SerializedName("message") val message: String? = null,
    @SerializedName("code") val code: String? = null
)