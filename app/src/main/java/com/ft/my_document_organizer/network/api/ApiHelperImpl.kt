package com.ft.my_document_organizer.network.api

import com.ft.my_document_organizer.data.model.registration.RegistrationResponse
import com.google.gson.JsonObject
import com.ft.my_document_organizer.data.model.*
import com.ft.my_document_organizer.data.model.change_password.ChangePasswordResponse
import com.ft.my_document_organizer.data.model.email_verify.VerificationResponse
import com.ft.my_document_organizer.data.model.forgot_password.ForgotPasswordResponse
import com.ft.my_document_organizer.data.model.login.login.google_signing.GoogleLoginResponse
import com.ft.my_document_organizer.data.model.login.login.user_login.LoginResponse
import com.ft.my_document_organizer.data.model.otp.otp_verify.OtpVerifyResponse
import com.ft.my_document_organizer.data.model.otp.resend_otp.ResendOtpResponse
import com.ft.my_document_organizer.data.model.reset_password.ResetPasswordResponse
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiInterface: ApiInterface) : ApiHelper {

    override suspend fun searchNews(query: String, pageNumber: Int): Response<NewsResponse> {
        return apiInterface.searchNews(query, pageNumber)
    }

    override suspend fun getNews(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return apiInterface.getNews(countryCode, pageNumber)
    }

    override suspend fun login(map: HashMap<String, String>): Response<LoginResponse> {
        return apiInterface.login(map)
    }

    override suspend fun forgotPassword(map: HashMap<String, String>): Response<ForgotPasswordResponse> {
        return apiInterface.forgotPassword(map)
    }

    override suspend fun otpVerify(
        map: HashMap<String, String>,
        token: String
    ): Response<OtpVerifyResponse> {
        return apiInterface.otpVerify(map, token)
    }

    override suspend fun resetPassword(map: HashMap<String, String>, token: String): Response<ResetPasswordResponse> {
        return apiInterface.resetPassword(map, token)
    }

    override suspend fun changePassword(map: HashMap<String, String>, token: String): Response<ChangePasswordResponse> {
        return apiInterface.changePassword(map, token)
    }

    override suspend fun googleLogin(map: HashMap<String, String>): Response<GoogleLoginResponse> {
        return apiInterface.googleLogin(map)
    }

    override suspend fun registration(map: HashMap<String, String>): Response<RegistrationResponse> {
        return apiInterface.registration(map)
    }

    override suspend fun emailVerification(
        map: HashMap<String, String>,
        token: String
    ): Response<VerificationResponse> {
        return apiInterface.emailVerification(map, token)
    }

    override suspend fun resentOTP(map: HashMap<String, String>): Response<ResendOtpResponse> {
        return apiInterface.resentOTP(map)
    }

    override suspend fun uploadFile(
        image: String?,
        album: String?,
        type: String?,
        name: String?,
        title: String?,
        description: String?
    ): Call<*> {
        return uploadFile(image, album, type, name, title, description)
    }

    override suspend fun sync(map: HashMap<String, String>): Call<JsonObject> {
        return sync(map)
    }

}