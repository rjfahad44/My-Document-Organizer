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

interface ApiHelper {
    suspend fun login(map: HashMap<String, String>): Response<LoginResponse>
    suspend fun googleLogin(map: HashMap<String, String>): Response<GoogleLoginResponse>
    suspend fun registration(map: HashMap<String, String>): Response<RegistrationResponse>
    suspend fun emailVerification(map: HashMap<String, String>, token: String): Response<VerificationResponse>
    suspend fun resentOTP(map: HashMap<String, String>): Response<ResendOtpResponse>
    suspend fun forgotPassword(map: HashMap<String, String>): Response<ForgotPasswordResponse>
    suspend fun otpVerify(map: HashMap<String, String>, token: String): Response<OtpVerifyResponse>
    suspend fun resetPassword(map: HashMap<String, String>, token: String): Response<ResetPasswordResponse>
    suspend fun changePassword(map: HashMap<String, String>, token: String): Response<ChangePasswordResponse>


    suspend fun searchNews(query: String, pageNumber: Int): Response<NewsResponse>
    suspend fun getNews(countryCode: String, pageNumber: Int): Response<NewsResponse>
    suspend fun uploadFile(image: String?, album: String?, type: String?, name: String?, title: String?, description: String?): Call<*>
    suspend fun sync(map: HashMap<String, String>): Call<JsonObject>
}