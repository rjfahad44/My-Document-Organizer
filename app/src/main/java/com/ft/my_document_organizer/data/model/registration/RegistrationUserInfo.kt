package com.ft.my_document_organizer.data.model.registration

import com.google.gson.annotations.SerializedName


data class RegistrationUserInfo(
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("email_verify_at") var emailVerifyAt: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null
)