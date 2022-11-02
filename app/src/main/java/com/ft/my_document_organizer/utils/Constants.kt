
package com.ft.my_document_organizer.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.ft.my_document_organizer.BuildConfig
import com.ft.my_document_organizer.DocApplication
import java.util.*

class Constants {

    @SuppressLint("HardwareIds")
    companion object {
        const val API_KEY = "d38033a10b9f40cf841cab1b3ca9d888"
        const val searchTimeDelay = 500L
        const val QUERY_PER_PAGE = 10
        const val DEFAULT_PAGE_INDEX = 1
        const val CountryCode = "bd"
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val EXTRA_IMAGE_CAMERA = "EXTRA_IMAGE_CAMERA"

        const val BASE_URL = "http://192.168.50.103:3000/api/v1/"
//        const val BASE_URL = "https://newsapi.org"

        // Device Information
        val HEIGHT by lazy { DocApplication.context.resources.displayMetrics.heightPixels }
        val WIDTH by lazy { DocApplication.context.resources.displayMetrics.widthPixels }
        val SDK by lazy { Build.VERSION.SDK_INT }
        val DEVICE_MODEL by lazy { Build.MODEL }
        val BRAND by lazy { Build.BRAND }
        val DEVICE_ID by lazy {
            Settings.Secure.getString(
                DocApplication.context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }
        val APP_ID by lazy { BuildConfig.APPLICATION_ID }
        val BUILD_TYPE by lazy { BuildConfig.BUILD_TYPE }
        val VERSION_CODE by lazy { BuildConfig.VERSION_CODE }
        val VERSION_NAME by lazy { BuildConfig.VERSION_NAME }
        val ZONE_ID by lazy { TimeZone.getDefault().id }
        val ZONE_NAME by lazy { TimeZone.getDefault().displayName }

        // Login Keys
        const val LOGIN_INFO = "login_info"
        const val LABEL_SORT = "login_sort"
        const val SORT = "sort"
        val EMAIL_ACCOUNTS_DATABASE_CONTENT_URI: Uri = Uri.parse("content://com.android.email.provider/account")
        const val WORKER_NAME = "worker_name"


        /*-------------------------------------Status Code----------------------------------------*/
        const val HTTP_200_OK = 200
        const val HTTP_201_CREATED = 201
        const val HTTP_202_ACCEPTED = 202
        const val HTTP_401_UNAUTHORIZED = 401
        const val HTTP_403_FORBIDDEN = 403
        const val HTTP_404_NOT_FOUND = 404
        const val HTTP_406_NOT_ACCEPTABLE = 406
        const val HTTP_422_UNPROCESSABLE_ENTITY = 422


        /*-------------------------------------Common Status Code---------------------------------*/
        const val COMMON_NOT_FOUND = HTTP_404_NOT_FOUND
        const val COMMON_UNPROCESSABLE_ENTITY = HTTP_422_UNPROCESSABLE_ENTITY


        /*-----------------------------------Login Status Code------------------------------------*/
        const val LOGIN_SUCCESS = HTTP_202_ACCEPTED
        const val LOGIN_FAILED = HTTP_406_NOT_ACCEPTABLE
        const val LOGIN_FORBIDDEN = HTTP_403_FORBIDDEN


        /*------------------------------Registration Status Code----------------------------------*/
        const val REGISTRATION_SUCCESS = HTTP_201_CREATED


        /*---------------------------Email verification Status Code-------------------------------*/
        const val EMAIL_VERIFICATION_SUCCESS = HTTP_200_OK


        /*--------------------------------Resend OTP Status Code----------------------------------*/
        const val OTP_SEND_SUCCESS = HTTP_200_OK


        /*--------------------------Forgot Password Email Status Code-----------------------------*/
        const val FORGOT_PASSWORD_SUCCESS = HTTP_200_OK


        /*----------------------------------OTP Verify Status Code---------------------------------*/
        const val OTP_VERIFY_SUCCESS = HTTP_202_ACCEPTED


        /*----------------------------------Reset Password Status Code---------------------------------*/
        const val RESET_PASSWORD_SUCCESS = HTTP_200_OK

        /*----------------------------------Google Signing---------------------------------*/
        const val GOOGLE_SIGNING_SUCCESS = HTTP_202_ACCEPTED



    }
}