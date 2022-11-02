package com.ft.my_document_organizer.data.model.registration

import com.google.gson.annotations.SerializedName


data class RegistrationResponse(
    @SerializedName("type") var type: String? = null,
    @SerializedName("code") var code: Int? = null,
    @SerializedName("http_code") var http_code: Int? = null,
    @SerializedName("data") var data: RegistrationData? = RegistrationData()
)