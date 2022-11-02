package com.ft.my_document_organizer.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ft.my_document_organizer.data.local.DataStoreImplRepository
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
import com.ft.my_document_organizer.di.CoroutinesDispatcherProvider
import com.ft.my_document_organizer.network.repository.user_repository.IUserRepository
import com.ft.my_document_organizer.state.NetworkState
import com.ft.my_document_organizer.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class UserViewModel @Inject constructor(
    private val iUserRepository: IUserRepository,
    private val dataStoreImplRepository: DataStoreImplRepository,
    private val networkHelper: NetworkHelper,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {

    private val TAG = "UserViewModel"
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    // User Registration
    private val _registrationResponse =
        MutableStateFlow<NetworkState<RegistrationResponse>>(NetworkState.Empty())
    val registrationResponse: StateFlow<NetworkState<RegistrationResponse>> get() = _registrationResponse
    private var registrationResponseObject: RegistrationResponse? = null

    // Email Verification
    private val _emailVerificationResponse =
        MutableStateFlow<NetworkState<VerificationResponse>>(NetworkState.Empty())
    val emailVerificationResponse: StateFlow<NetworkState<VerificationResponse>> get() = _emailVerificationResponse
    private var emailVerificationResponseObject: VerificationResponse? = null

    // Resend OTP
    private val _resendOtpResponse =
        MutableStateFlow<NetworkState<ResendOtpResponse>>(NetworkState.Empty())
    val resendOtpResponse: StateFlow<NetworkState<ResendOtpResponse>> get() = _resendOtpResponse
    private var resendOtpResponseObject: ResendOtpResponse? = null

    // Login
    private val _loginResponse = MutableStateFlow<NetworkState<LoginResponse>>(NetworkState.Empty())
    val loginResponse: StateFlow<NetworkState<LoginResponse>> get() = _loginResponse
    private var loginResponseObject: LoginResponse? = null


    // Google Login
    private val _googleLoginResponse = MutableStateFlow<NetworkState<GoogleLoginResponse>>(NetworkState.Empty())
    val googleLoginResponse: StateFlow<NetworkState<GoogleLoginResponse>> get() = _googleLoginResponse
    private var googleLoginResponseObject: GoogleLoginResponse? = null


    // Forgot Password
    private val _forgotPasswordResponse =
        MutableStateFlow<NetworkState<ForgotPasswordResponse>>(NetworkState.Empty())
    val forgotPasswordResponse: StateFlow<NetworkState<ForgotPasswordResponse>> get() = _forgotPasswordResponse
    private var forgotPasswordResponseObject: ForgotPasswordResponse? = null

    // OTP Verify
    private val _otpVerifyResponse =
        MutableStateFlow<NetworkState<OtpVerifyResponse>>(NetworkState.Empty())
    val otpVerifyResponse: StateFlow<NetworkState<OtpVerifyResponse>> get() = _otpVerifyResponse
    private var otpVerifyResponseObject: OtpVerifyResponse? = null


    // Reset Password
    private val _resetPasswordResponse =
        MutableStateFlow<NetworkState<ResetPasswordResponse>>(NetworkState.Empty())
    val resetPasswordResponse: StateFlow<NetworkState<ResetPasswordResponse>> get() = _resetPasswordResponse
    private var resetPasswordResponseObject: ResetPasswordResponse? = null


    // Change Password
    private val _changePasswordResponse =
        MutableStateFlow<NetworkState<ChangePasswordResponse>>(NetworkState.Empty())
    val changePasswordResponse: StateFlow<NetworkState<ChangePasswordResponse>> get() = _changePasswordResponse
    private var changePasswordResponseObject: ChangePasswordResponse? = null

    var userId : MutableLiveData<String> = MutableLiveData("")
    var userEmail : MutableLiveData<String> = MutableLiveData("")
    var accessToken : MutableLiveData<String> = MutableLiveData("")
    var tokenType : MutableLiveData<String> = MutableLiveData("")
    var loginStatus : MutableLiveData<Boolean> = MutableLiveData(false)
    var loginInfo : MutableLiveData<LoginInfo> = MutableLiveData()

    init {
        // first api call when this viewmodel just initialized.
//        fetchNews(Constants.CountryCode)
        getUserLoginInfo()
    }

    /*------------------------------------User Registration---------------------------------------*/
    fun registration(map: HashMap<String, String>) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _registrationResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.registration(map)) {
                    is NetworkState.Success -> {
                        _registrationResponse.value = handleRegistrationResponse(response)
                        Timber.tag(TAG).d("${_registrationResponse.value}")
                    }
                    is NetworkState.Error -> {
                        _registrationResponse.value = NetworkState.Error(
                            response.data?.data?.message ?: response.message.toString()
                        )
                    }
                    else -> {}
                }
            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleRegistrationResponse(response: NetworkState<RegistrationResponse>): NetworkState<RegistrationResponse> {
        response.data?.let { resultResponse ->
            if (this.registrationResponseObject == null) {
                this.registrationResponseObject = resultResponse
            }
            //Conversion
            this.registrationResponse.let {
                Timber.tag(TAG).d("${this.registrationResponseObject!!.code}")

                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.registrationResponse.value.data?.data?.message
                ?: this.registrationResponse.value.message.toString()
        )
    }


    /*------------------------------------Email Verification--------------------------------------*/
    fun emailVerification(map: HashMap<String, String>, token: String) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _emailVerificationResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.emailVerification(map, token)) {
                    is NetworkState.Success -> {
                        _emailVerificationResponse.value = handleEmailVerificationResponse(response)
                        Timber.tag(TAG).d("${_emailVerificationResponse.value}")
                        Timber.d(TAG,_emailVerificationResponse.value)
                    }
                    is NetworkState.Error -> {
                        _emailVerificationResponse.value =
                            NetworkState.Error(
                                response.data?.data?.message ?: response.message.toString()
                            )
                    }
                    else -> {}
                }

            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleEmailVerificationResponse(response: NetworkState<VerificationResponse>): NetworkState<VerificationResponse> {
        response.data?.let { resultResponse ->
            if (this.emailVerificationResponseObject == null) {
                this.emailVerificationResponseObject = resultResponse
            }
            //Conversion
            this.emailVerificationResponse.let {
                Timber.tag(TAG).d("${this.emailVerificationResponseObject}")

                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.emailVerificationResponse.value.data?.data?.message
                ?: this.emailVerificationResponse.value.message.toString()
        )
    }


    /*----------------------------------------Resend OTP------------------------------------------*/
    fun reSendOTP(map: HashMap<String, String>) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _resendOtpResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.resendOTP(map)) {
                    is NetworkState.Success -> {
                        _resendOtpResponse.value = handleReSendOTPResponse(response)
                        Timber.tag(TAG).d("${_resendOtpResponse.value}")
                    }
                    is NetworkState.Error -> {
                        _resendOtpResponse.value =
                            NetworkState.Error(
                                response.data?.data?.message ?: response.message.toString()
                            )
                    }
                    else -> {}
                }

            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleReSendOTPResponse(response: NetworkState<ResendOtpResponse>): NetworkState<ResendOtpResponse> {
        response.data?.let { resultResponse ->
            if (this.resendOtpResponseObject == null) {
                this.resendOtpResponseObject = resultResponse
            }
            //Conversion
            this.resendOtpResponse.let {
                Timber.tag(TAG).d("${this.resendOtpResponseObject}")

                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.resendOtpResponse.value.data?.data?.message
                ?: this.resendOtpResponse.value.message.toString()
        )
    }


    /*-------------------------------------------Login--------------------------------------------*/
    fun login(map: HashMap<String, String>) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _loginResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.login(map)) {
                    is NetworkState.Success -> {
                        _loginResponse.value = handleLoginResponse(response)
                        Timber.tag(TAG).d("${_loginResponse.value}")
                        Timber.d(TAG,_otpVerifyResponse.value)
                        Timber.d(TAG,_otpVerifyResponse.value.data)
                    }
                    is NetworkState.Error -> {
                        //TODO: Need to add conditional error message here
                        _loginResponse.value = NetworkState.Error(
                            response.data?.data?.message ?: response.message.toString()
                        )
                    }
                    else -> {}
                }
            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleLoginResponse(response: NetworkState<LoginResponse>): NetworkState<LoginResponse> {
        response.data?.let { resultResponse ->
            if (this.loginResponseObject == null) {
                this.loginResponseObject = resultResponse
            }
            //Conversion
            this.loginResponse.let {
                Timber.tag(TAG).d("${this.loginResponseObject!!.code}")
                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.loginResponse.value.data?.data?.message
                ?: this.loginResponse.value.message.toString()
        )
    }


    /*---------------------------------------Google Login-----------------------------------------*/
    fun googleLogin(map: HashMap<String, String>) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _googleLoginResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.googleLogin(map)) {
                    is NetworkState.Success -> {
                        _googleLoginResponse.value = handleGoogleLoginResponse(response)
                        Timber.tag(TAG).d("${_googleLoginResponse.value}")
                    }
                    is NetworkState.Error -> {
                        //TODO: Need to add conditional error message here
                        _googleLoginResponse.value = NetworkState.Error(
                            response.data?.data?.message ?: response.message.toString()
                        )
                    }
                    else -> {}
                }
            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleGoogleLoginResponse(response: NetworkState<GoogleLoginResponse>): NetworkState<GoogleLoginResponse> {
        response.data?.let { resultResponse ->
            if (this.googleLoginResponseObject == null) {
                this.googleLoginResponseObject = resultResponse
            }
            //Conversion
            this.googleLoginResponse.let {
                Timber.tag(TAG).d("${this.googleLoginResponseObject!!.code}")
                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.googleLoginResponse.value.data?.data?.message
                ?: this.googleLoginResponse.value.message.toString()
        )
    }


    /*--------------------------------------Forgot Password---------------------------------------*/
    fun forgotPassword(map: HashMap<String, String>) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _forgotPasswordResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.forgotPassword(map)) {
                    is NetworkState.Success -> {
                        _forgotPasswordResponse.value = handleForgotPasswordResponse(response)
                        Timber.tag(TAG).d("${_forgotPasswordResponse.value}")
                    }
                    is NetworkState.Error -> {
                        _forgotPasswordResponse.value =
                            NetworkState.Error(
                                response.data?.data?.message ?: response.message.toString()
                            )
                    }
                    else -> {}
                }

            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleForgotPasswordResponse(response: NetworkState<ForgotPasswordResponse>): NetworkState<ForgotPasswordResponse> {
        response.data?.let { resultResponse ->
            if (this.forgotPasswordResponseObject == null) {
                this.forgotPasswordResponseObject = resultResponse
            }
            //Conversion
            this.resendOtpResponse.let {
                Timber.tag(TAG).d("${this.forgotPasswordResponseObject}")

                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.forgotPasswordResponse.value.data?.data?.message
                ?: this.forgotPasswordResponse.value.message.toString()
        )
    }

    /*----------------------------------------OTP Verify------------------------------------------*/
    fun otpVerify(map: HashMap<String, String>, token: String) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _otpVerifyResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.otpVerify(map, token)) {
                    is NetworkState.Success -> {
                        _otpVerifyResponse.value = handleOtpVerifyResponse(response)
                        Timber.tag(TAG).d("${_otpVerifyResponse.value}")
                        Timber.d(TAG,_otpVerifyResponse.value.data)
                    }
                    is NetworkState.Error -> {
                        _otpVerifyResponse.value =
                            NetworkState.Error(
                                response.data?.data?.message ?: response.message.toString()
                            )
                    }
                    else -> {}
                }

            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleOtpVerifyResponse(response: NetworkState<OtpVerifyResponse>): NetworkState<OtpVerifyResponse> {
        response.data?.let { resultResponse ->
            if (this.otpVerifyResponseObject == null) {
                this.otpVerifyResponseObject = resultResponse
            }
            //Conversion
            this.emailVerificationResponse.let {
                Timber.tag(TAG).d("${this.otpVerifyResponseObject}")

                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.otpVerifyResponse.value.data?.data?.message
                ?: this.otpVerifyResponse.value.message.toString()
        )
    }


    /*-------------------------------------Reset Password-----------------------------------------*/
    fun resetPassword(map: HashMap<String, String>, token: String) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _resetPasswordResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.resetPassword(map, token)) {
                    is NetworkState.Success -> {
                        _resetPasswordResponse.value = handleResetPasswordResponse(response)
                        Timber.tag(TAG).d("${_resetPasswordResponse.value}")
                    }
                    is NetworkState.Error -> {
                        _resetPasswordResponse.value = NetworkState.Error(
                            response.data?.data?.message ?: response.message.toString()
                        )
                    }
                    else -> {}
                }
            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleResetPasswordResponse(response: NetworkState<ResetPasswordResponse>): NetworkState<ResetPasswordResponse> {
        response.data?.let { resultResponse ->
            if (this.resetPasswordResponseObject == null) {
                this.resetPasswordResponseObject = resultResponse
            }
            //Conversion
            this.resetPasswordResponse.let {
                Timber.tag(TAG).d("${this.resetPasswordResponseObject?.code}")

                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.resetPasswordResponse.value.data?.data?.message
                ?: this.resetPasswordResponse.value.message.toString()
        )
    }

    /*-------------------------------------Change Password----------------------------------------*/
    fun changePassword(map: HashMap<String, String>, token: String) {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _changePasswordResponse.value = NetworkState.Loading()
                when (val response = iUserRepository.changePassword(map, token)) {
                    is NetworkState.Success -> {
                        _changePasswordResponse.value = handleChangePasswordResponse(response)
                        Timber.tag(TAG).d("${_changePasswordResponse.value}")
                    }
                    is NetworkState.Error -> {
                        _changePasswordResponse.value = NetworkState.Error(
                            response.data?.data?.message ?: response.message.toString()
                        )
                    }
                    else -> {}
                }
            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    private fun handleChangePasswordResponse(response: NetworkState<ChangePasswordResponse>): NetworkState<ChangePasswordResponse> {
        response.data?.let { resultResponse ->
            if (this.changePasswordResponseObject == null) {
                this.changePasswordResponseObject = resultResponse
            }
            //Conversion
            this.changePasswordResponse.let {
                Timber.tag(TAG).d("${this.changePasswordResponseObject?.code}")

                // todo: user phone or email verification code can be add here.
            }
            return NetworkState.Success(resultResponse)
        }
        return NetworkState.Error(
            this.changePasswordResponse.value.data?.data?.message
                ?: this.changePasswordResponse.value.message.toString()
        )
    }

    fun hideErrorToast() {
        _errorMessage.value = ""
    }

    private fun onError(throwable: Throwable) {
        throwable.message?.let {
            _errorMessage.value = it
        }
    }

    // Datastore related methods
    fun saveUserLoginInfo(){
        viewModelScope.launch(coroutinesDispatcherProvider.io){
            dataStoreImplRepository.saveUserLoginInfo(
                LoginInfo(userId = userId.value!!,
                    userEmail = userEmail.value!!,
                    accessToken = accessToken.value!!,
                    tokenType = tokenType.value!!,
                    loginStatus = loginStatus.value!!)
            )
        }
    }

    fun getUserLoginInfo(){
        viewModelScope.launch(coroutinesDispatcherProvider.io){
            dataStoreImplRepository.getLoginInfo().collect(){
                loginInfo.postValue(it)
            }
        }
    }

}