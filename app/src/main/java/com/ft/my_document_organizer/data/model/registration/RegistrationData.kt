package com.ft.my_document_organizer.data.model.registration

import com.google.gson.annotations.SerializedName


data class RegistrationData(
    @SerializedName("message") var message: String? = null,
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("user_info") var userInfo: RegistrationUserInfo? = RegistrationUserInfo()
)