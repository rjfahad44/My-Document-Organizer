package com.ft.my_document_organizer

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import com.ft.my_document_organizer.TemplateData.insertTemplateCategories
import com.ft.my_document_organizer.TemplateData.insertTemplateInputs
import com.ft.my_document_organizer.data.local.DocDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import timber.log.Timber

@HiltAndroidApp
class DocApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Application
        lateinit var sp: SharedPreferences
        private val scope = CoroutineScope(Dispatchers.IO)
    }

    private val TAG = "DocApplication"
    override fun onCreate() {
        super.onCreate()
        context = applicationContext as Application
        if (BuildConfig.DEBUG) {
            //Debug Mode
            Timber.plant(Timber.DebugTree())
        }
        scope.launch {
            try {
                val database = DocDatabase.getDatabase().getDocDao()
                Timber.tag(TAG).d( "database created.")
                Timber.tag(TAG).d( "create status.. ${database.all.isEmpty()}")
                if (database.all.isEmpty()) {
                    insertTemplateInputs()
                    insertTemplateCategories()
                }
            } catch (e: Exception) {
                Timber.tag(TAG).d("run.. ${e.message}")
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }
}