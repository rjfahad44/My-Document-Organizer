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
import com.ft.my_document_organizer.utils.Constants.Companion.API_KEY
import com.ft.my_document_organizer.utils.Constants.Companion.CountryCode
import com.ft.my_document_organizer.utils.Constants.Companion.QUERY_PER_PAGE
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode: String = CountryCode,
        @Query("page")
        pageNumber: Int = 1,
        @Query("pageSize")
        pageSize: Int = QUERY_PER_PAGE,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("pageSize")
        pageSize: Int = QUERY_PER_PAGE,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/login")
    suspend fun login(@FieldMap map: HashMap<String, String>): Response<LoginResponse>

    @FormUrlEncoded
    @POST("auth/google-login")
    suspend fun googleLogin(@FieldMap map: HashMap<String, String>): Response<GoogleLoginResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/register")
    suspend fun registration(@FieldMap map: HashMap<String, String>): Response<RegistrationResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/send-otp")
    suspend fun resentOTP(@FieldMap map: HashMap<String, String>): Response<ResendOtpResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("auth/verify-email")
    suspend fun emailVerification(
        @FieldMap map: HashMap<String, String>,
        @Header("Authorization") token: String
    ): Response<VerificationResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@FieldMap map: HashMap<String, String>): Response<ForgotPasswordResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/verify-otp")
    suspend fun otpVerify(
        @FieldMap map: HashMap<String, String>,
        @Header("Authorization") token: String
    ): Response<OtpVerifyResponse>


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("auth/reset-password")
    suspend fun resetPassword(@FieldMap map: HashMap<String, String>, @Header("Authorization") token: String): Response<ResetPasswordResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("user/change-password")
    suspend fun changePassword(@FieldMap map: HashMap<String, String>, @Header("Authorization") token: String): Response<ChangePasswordResponse>

    @Multipart
    @POST("user/upload")
    suspend fun uploadFile(
        @Part("image") image: String?,
        @Part("album") album: String?,
        @Part("type") type: String?,
        @Part("name") name: String?,
        @Part("title") title: String?,
        @Part("description") description: String?
    ): Call<*>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("user/sync")
    suspend fun sync(@FieldMap map: HashMap<String, String>): Call<JsonObject>

}