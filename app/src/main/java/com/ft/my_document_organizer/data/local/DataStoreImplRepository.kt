package com.ft.my_document_organizer.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ft.my_document_organizer.data.model.LoginInfo
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit

const val Datastore_Name = "Document_Organizer"
val Context.datastore : DataStore<Preferences> by  preferencesDataStore(name = Datastore_Name)

class DataStoreImplRepository(private val context: Context) : DataStoreAbstract {

    companion object{
        val USER_ID = stringPreferencesKey("USER_ID")
        val USER_EMAIL = stringPreferencesKey("USER_EMAIL")
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val TOKEN_TYPE = stringPreferencesKey("TOKEN_TYPE")
        val LOGIN_STATUS = booleanPreferencesKey("LOGIN_STATUS")
    }

    override suspend fun saveUserLoginInfo(loginInfo: LoginInfo) {
        context.datastore.edit { loginInfoData ->
            loginInfoData[USER_ID] = loginInfo.userId
            loginInfoData[USER_EMAIL] = loginInfo.userEmail
            loginInfoData[ACCESS_TOKEN] = loginInfo.accessToken
            loginInfoData[TOKEN_TYPE] = loginInfo.tokenType
            loginInfoData[LOGIN_STATUS] = loginInfo.loginStatus

        }
    }

    override suspend fun getLoginInfo() = context.datastore.data.map { loginInfo ->
        LoginInfo(
            userId = loginInfo[USER_ID]!!,
            userEmail = loginInfo[USER_EMAIL]!!,
            accessToken =  loginInfo[ACCESS_TOKEN]!!,
            tokenType = loginInfo[TOKEN_TYPE]!!,
            loginStatus = loginInfo[LOGIN_STATUS]!!
        )
    }
}