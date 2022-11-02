package com.ft.my_document_organizer.data.local

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ft.my_document_organizer.data.local.Converters
import com.ft.my_document_organizer.DocApplication.Companion.context
import com.ft.my_document_organizer.data.local.dao.DocDao
import com.ft.my_document_organizer.data.local.dao.RelationalUserDao
import com.ft.my_document_organizer.data.local.dao.TemplateDao
import com.ft.my_document_organizer.data.local.dao.UserDao
import com.ft.my_document_organizer.data.model.*

@Database(
    entities = [
        RelationUserDocumentLabels::class,
        RelationUserDocumentTypesCategories::class,
        TemplateCategories::class,
        TemplateInputs::class,
        UserCategories::class,
        UserDocumentFiles::class,
        UserDocuments::class,
        UserDocumentTypes::class,
        UserLabels::class,
        UserNotes::class,
        UserReminders::class],
    version = DocDatabase.DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class DocDatabase : RoomDatabase() {

    abstract fun getDocDao(): DocDao
    abstract fun insertTemplateDao(): TemplateDao
    abstract fun UserDao(): UserDao
    abstract fun relationalUserDao(): RelationalUserDao

    companion object {
        const val DATABASE_VERSION = 1

        private var sInstance: DocDatabase? = null

        val callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.i("DocDatabase", "onCreate ")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.i("DocDatabase", "onOpen ")
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                Log.i("DocDatabase", "onDestructiveMigration ")
            }
        }

        private var INSTANCE : DocDatabase? = null
        fun getDatabase() : DocDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DocDatabase::class.java,
                    "document_organizer_db")
                    //.addAutoMigrationSpec(AutoMigration)
                    .addCallback(callback)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}