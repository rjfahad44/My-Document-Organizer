package com.ft.my_document_organizer.network.repository.user_repository

import androidx.lifecycle.LiveData
import com.ft.my_document_organizer.data.model.registration.RegistrationResponse
import com.ft.my_document_organizer.data.model.*
import com.ft.my_document_organizer.data.model.change_password.ChangePasswordResponse
import com.ft.my_document_organizer.data.model.email_verify.VerificationResponse
import com.ft.my_document_organizer.data.model.forgot_password.ForgotPasswordResponse
import com.ft.my_document_organizer.data.model.login.login.google_signing.GoogleLoginResponse
import com.ft.my_document_organizer.data.model.login.login.user_login.LoginResponse
import com.ft.my_document_organizer.data.model.otp.otp_verify.OtpVerifyResponse
import com.ft.my_document_organizer.data.model.otp.resend_otp.ResendOtpResponse
import com.ft.my_document_organizer.data.model.reset_password.ResetPasswordResponse
import com.ft.my_document_organizer.state.NetworkState

interface IUserRepository {
    suspend fun login(map: HashMap<String, String>): NetworkState<LoginResponse>
    suspend fun googleLogin(map: HashMap<String, String>): NetworkState<GoogleLoginResponse>
    suspend fun registration(map: HashMap<String, String>): NetworkState<RegistrationResponse>
    suspend fun emailVerification(map: HashMap<String, String>, token: String): NetworkState<VerificationResponse>
    suspend fun resendOTP(map: HashMap<String, String>): NetworkState<ResendOtpResponse>
    suspend fun forgotPassword(map: HashMap<String, String>): NetworkState<ForgotPasswordResponse>
    suspend fun otpVerify(map: HashMap<String, String>, token: String): NetworkState<OtpVerifyResponse>
    suspend fun resetPassword(map: HashMap<String, String>, token: String): NetworkState<ResetPasswordResponse>
    suspend fun changePassword(map: HashMap<String, String>, token: String): NetworkState<ChangePasswordResponse>


    suspend fun searchNews(searchQuery: String, pageNumber: Int): NetworkState<NewsResponse>

    suspend fun saveNews(news: NewsArticle): Long

    fun getSavedNews(): LiveData<List<NewsArticle>>

    suspend fun deleteNews(news: NewsArticle)

    suspend fun deleteAllNews()
}
