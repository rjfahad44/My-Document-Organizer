package com.ft.my_document_organizer.data.local

import com.ft.my_document_organizer.data.model.LoginInfo
import kotlinx.coroutines.flow.Flow

interface DataStoreAbstract {

    suspend fun saveUserLoginInfo(loginInfo: LoginInfo)

    suspend fun getLoginInfo(): Flow<LoginInfo>

}