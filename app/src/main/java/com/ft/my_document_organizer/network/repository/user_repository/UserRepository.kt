package com.ft.my_document_organizer.network.repository.user_repository

import androidx.lifecycle.LiveData
import com.ft.my_document_organizer.data.model.registration.RegistrationResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ft.my_document_organizer.data.local.dao.DocDao
import com.ft.my_document_organizer.data.model.*
import com.ft.my_document_organizer.data.model.change_password.ChangePasswordResponse
import com.ft.my_document_organizer.data.model.email_verify.VerificationResponse
import com.ft.my_document_organizer.data.model.forgot_password.ForgotPasswordResponse
import com.ft.my_document_organizer.data.model.login.login.google_signing.GoogleLoginResponse
import com.ft.my_document_organizer.data.model.login.login.user_login.LoginResponse
import com.ft.my_document_organizer.data.model.otp.otp_verify.OtpVerifyResponse
import com.ft.my_document_organizer.data.model.otp.resend_otp.ResendOtpResponse
import com.ft.my_document_organizer.data.model.reset_password.ResetPasswordResponse
import com.ft.my_document_organizer.network.api.ApiHelper
import com.ft.my_document_organizer.state.NetworkState
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: ApiHelper,
    private val docDao: DocDao
) : IUserRepository {

    val allRelationUserDocumentLabels: LiveData<MutableList<RelationUserDocumentLabels>> =
        docDao.getAllRelationUserDocumentLabels()
    val allRelationUserDocumentTypesCategories: LiveData<MutableList<RelationUserDocumentTypesCategories>> =
        docDao.getAllRelationUserDocumentTypesCategories()
    val allTemplateCategories: LiveData<MutableList<TemplateCategories>> =
        docDao.getAllTemplateCategories()
    val allTemplateInputs: LiveData<MutableList<TemplateInputs>> = docDao.getAllTemplateInputs()
    val allUserCategories: LiveData<MutableList<UserCategories>> = docDao.getAllUserCategories()
    val allUserDocumentFiles: LiveData<MutableList<UserDocumentFiles>> =
        docDao.getAllUserDocumentFiles()
    val allUserDocuments: LiveData<MutableList<UserDocuments>> = docDao.getAllUserDocuments()
    val allUserDocumentTypes: LiveData<MutableList<UserDocumentTypes>> =
        docDao.getAllUserDocumentTypes()
    val allUserLabels: LiveData<MutableList<UserLabels>> = docDao.getAllUserLabels()
    val allUserNotes: LiveData<MutableList<UserNotes>> = docDao.getAllUserNotes()
    val allUserReminders: LiveData<MutableList<UserReminders>> = docDao.getAllUserReminders()

    /*------------------------------RelationUserDocumentLabels------------------------------------*/
    suspend fun insert(relationUserDocumentLabels: RelationUserDocumentLabels) {
        docDao.insert(relationUserDocumentLabels)
    }

    suspend fun update(relationUserDocumentLabels: RelationUserDocumentLabels) {
        docDao.update(relationUserDocumentLabels)
    }

    suspend fun delete(relationUserDocumentLabels: RelationUserDocumentLabels) {
        docDao.delete(relationUserDocumentLabels)
    }

    /*----------------------------RelationUserDocumentTypesCategories-----------------------------*/
    suspend fun insert(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) {
        docDao.insert(relationUserDocumentTypesCategories)
    }

    suspend fun update(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) {
        docDao.update(relationUserDocumentTypesCategories)
    }

    suspend fun delete(relationUserDocumentTypesCategories: RelationUserDocumentTypesCategories) {
        docDao.delete(relationUserDocumentTypesCategories)
    }


    /*------------------------------------TemplateCategories--------------------------------------*/
    suspend fun insert(templateCategories: TemplateCategories) {
        docDao.insert(templateCategories)
    }

    suspend fun update(templateCategories: TemplateCategories) {
        docDao.update(templateCategories)
    }

    suspend fun delete(templateCategories: TemplateCategories) {
        docDao.delete(templateCategories)
    }


    /*--------------------------------------TemplateInputs----------------------------------------*/
    suspend fun insert(templateInputs: TemplateInputs) {
        docDao.insert(templateInputs)
    }

    suspend fun update(templateInputs: TemplateInputs) {
        docDao.update(templateInputs)
    }

    suspend fun delete(templateInputs: TemplateInputs) {
        docDao.delete(templateInputs)
    }


    /*--------------------------------------UserCategories----------------------------------------*/
    suspend fun insert(userCategories: UserCategories) {
        docDao.insert(userCategories)
    }

    suspend fun update(userCategories: UserCategories) {
        docDao.update(userCategories)
    }

    suspend fun delete(userCategories: UserCategories) {
        docDao.delete(userCategories)
    }


    /*-------------------------------------UserDocumentFiles--------------------------------------*/
    suspend fun insert(userDocumentFiles: UserDocumentFiles) {
        docDao.insert(userDocumentFiles)
    }

    suspend fun update(userDocumentFiles: UserDocumentFiles) {
        docDao.update(userDocumentFiles)
    }

    suspend fun delete(userDocumentFiles: UserDocumentFiles) {
        docDao.delete(userDocumentFiles)
    }


    /*---------------------------------------UserDocuments----------------------------------------*/
    suspend fun insert(userDocuments: UserDocuments) {
        docDao.insert(userDocuments)
    }

    suspend fun update(userDocuments: UserDocuments) {
        docDao.update(userDocuments)
    }

    suspend fun delete(userDocuments: UserDocuments) {
        docDao.delete(userDocuments)
    }


    /*-------------------------------------UserDocumentTypes--------------------------------------*/
    suspend fun insert(userDocumentTypes: UserDocumentTypes) {
        docDao.insert(userDocumentTypes)
    }

    suspend fun update(userDocumentTypes: UserDocumentTypes) {
        docDao.update(userDocumentTypes)
    }

    suspend fun delete(userDocumentTypes: UserDocumentTypes) {
        docDao.delete(userDocumentTypes)
    }


    /*-----------------------------------------UserLabels-----------------------------------------*/
    suspend fun insert(userLabels: UserLabels) {
        docDao.insert(userLabels)
    }

    suspend fun update(userLabels: UserLabels) {
        docDao.update(userLabels)
    }

    suspend fun delete(userLabels: UserLabels) {
        docDao.delete(userLabels)
    }


    /*-----------------------------------------UserNotes------------------------------------------*/
    suspend fun insert(userNotes: UserNotes) {
        docDao.insert(userNotes)
    }

    suspend fun update(userNotes: UserNotes) {
        docDao.update(userNotes)
    }

    suspend fun delete(userNotes: UserNotes) {
        docDao.delete(userNotes)
    }


    /*---------------------------------------UserReminders----------------------------------------*/
    suspend fun insert(userReminders: UserReminders) {
        docDao.insert(userReminders)
    }

    suspend fun update(userReminders: UserReminders) {
        docDao.update(userReminders)
    }

    suspend fun delete(userReminders: UserReminders) {
        docDao.delete(userReminders)
    }


    /*---------------------------------------Registration-----------------------------------------*/
    override suspend fun registration(
        map: HashMap<String, String>
    ): NetworkState<RegistrationResponse> {
        return try {
            val response = remoteDataSource.registration(map)
            val result = response.body()
            val type = object : TypeToken<RegistrationResponse>() {}.type
            val errorResponse: RegistrationResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }

            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /*-------------------------------------Email Verification-------------------------------------*/
    override suspend fun emailVerification(
        map: HashMap<String, String>,
        token: String
    ): NetworkState<VerificationResponse> {
        return try {
            val response = remoteDataSource.emailVerification(map, token)
            val result = response.body()
            val type = object : TypeToken<VerificationResponse>() {}.type
            val errorResponse: VerificationResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }

            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /*-----------------------------------------Resend OTP-----------------------------------------*/
    override suspend fun resendOTP(
        map: HashMap<String, String>
    ): NetworkState<ResendOtpResponse> {
        return try {
            val response = remoteDataSource.resentOTP(map)
            val result = response.body()

            val type = object : TypeToken<ResendOtpResponse>() {}.type
            val errorResponse: ResendOtpResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }

            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /*-------------------------------------------Login--------------------------------------------*/
    override suspend fun login(
        map: HashMap<String, String>
    ): NetworkState<LoginResponse> {
        return try {
            val response = remoteDataSource.login(map)
            val result = response.body()
            val type = object : TypeToken<LoginResponse>() {}.type
            val errorResponse: LoginResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }
            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /*---------------------------------------Google Login-----------------------------------------*/
    override suspend fun googleLogin(
        map: HashMap<String, String>
    ): NetworkState<GoogleLoginResponse> {
        return try {
            val response = remoteDataSource.googleLogin(map)
            val result = response.body()
            val type = object : TypeToken<GoogleLoginResponse>() {}.type
            val errorResponse: GoogleLoginResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }
            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /*--------------------------------------Forgot Password---------------------------------------*/
    override suspend fun forgotPassword(
        map: HashMap<String, String>
    ): NetworkState<ForgotPasswordResponse> {
        return try {
            val response = remoteDataSource.forgotPassword(map)

            val result = response.body()
            val type = object : TypeToken<ForgotPasswordResponse>() {}.type
            val errorResponse: ForgotPasswordResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }

            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /*-----------------------------------------OTP Verify-----------------------------------------*/
    override suspend fun otpVerify(
        map: HashMap<String, String>,
        token: String
    ): NetworkState<OtpVerifyResponse> {
        return try {
            val response = remoteDataSource.otpVerify(map, token)
            val result = response.body()
            val type = object : TypeToken<OtpVerifyResponse>() {}.type
            val errorResponse: OtpVerifyResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }

            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /*---------------------------------------Reset Password---------------------------------------*/
    override suspend fun resetPassword(map: HashMap<String, String>, token: String): NetworkState<ResetPasswordResponse> {
        return try {
            val response = remoteDataSource.resetPassword(map, token)
            val result = response.body()
            val type = object : TypeToken<ResetPasswordResponse>() {}.type
            val errorResponse: ResetPasswordResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }

            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /*--------------------------------------Change Password---------------------------------------*/
    override suspend fun changePassword(map: HashMap<String, String>, token: String): NetworkState<ChangePasswordResponse> {
        return try {
            val response = remoteDataSource.changePassword(map, token)
            val result = response.body()
            val type = object : TypeToken<ChangePasswordResponse>() {}.type
            val errorResponse: ChangePasswordResponse? =
                response.errorBody()?.let { Gson().fromJson(it.charStream(), type) }

            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                if (errorResponse != null) {
                    NetworkState.Success(errorResponse)
                } else {
                    NetworkState.Error("An error occurred ${errorResponse?.data?.message}")
                }
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }












    override suspend fun searchNews(
        searchQuery: String,
        pageNumber: Int
    ): NetworkState<NewsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(news: NewsArticle): Long {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): LiveData<List<NewsArticle>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(news: NewsArticle) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllNews() {
        TODO("Not yet implemented")
    }

}