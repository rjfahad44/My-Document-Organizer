package com.ft.my_document_organizer.data.model.change_password

import com.google.gson.annotations.SerializedName

data class ChangePasswordData(
    @SerializedName("message") val message: String? = null,
    @SerializedName("old_password") val old_password: String? = null,
    @SerializedName("new_password") val new_password: String? = null,
    @SerializedName("confirm_password") val confirm_password: String? = null
)