package com.ft.my_document_organizer.di

import android.content.Context
import com.ft.my_document_organizer.data.local.DocDatabase
import com.ft.my_document_organizer.data.local.dao.DocDao
import com.ft.my_document_organizer.network.api.ApiHelper
import com.ft.my_document_organizer.network.repository.doc_repository.DocRepository
import com.ft.my_document_organizer.network.repository.doc_repository.IDocRepository
import com.ft.my_document_organizer.network.repository.user_repository.IUserRepository
import com.ft.my_document_organizer.network.repository.user_repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        DocDatabase.getDatabase()

    @Singleton
    @Provides
    fun provideDocDao(db: DocDatabase) = db.getDocDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: ApiHelper,
        localDataSource: DocDao
    ) = DocRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideINewsRepository(repository: DocRepository): IDocRepository = repository

    @Singleton
    @Provides
    fun provideILoginRegistrationRepository(repository: UserRepository): IUserRepository = repository
}