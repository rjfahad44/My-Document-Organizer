package com.ft.my_document_organizer

import com.ft.my_document_organizer.data.local.DocDatabase
import com.ft.my_document_organizer.data.model.TemplateCategories
import com.ft.my_document_organizer.data.model.TemplateInputs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object TemplateData {
    private val database = DocDatabase.getDatabase().insertTemplateDao()
    suspend fun insertTemplateInputs() {
        withContext(Dispatchers.IO) {
            database.insert(
                TemplateInputs(
                    "a",
                    "25-08-2022",
                    "26-08-2022"
                )
            )

            database.insert(
                TemplateInputs(
                    "b",
                    "25-08-2022",
                    "26-08-2022"
                )
            )

            database.insert(
                TemplateInputs(
                    "c",
                    "25-08-2022",
                    "26-08-2022"
                )
            )

            database.insert(
                TemplateInputs(
                    "d",
                    "25-08-2022",
                    "26-08-2022"
                )
            )

        }
    }

    suspend fun insertTemplateCategories() {
        withContext(Dispatchers.IO) {

            database.insert(
                TemplateCategories(
                    "a",
                    "25-08-2022",
                    "26-08-2022"
                )
            )

            database.insert(
                TemplateCategories(
                    "b",
                    "25-08-2022",
                    "26-08-2022"
                )
            )

            database.insert(
                TemplateCategories(
                    "c",
                    "25-08-2022",
                    "26-08-2022"
                )
            )

            database.insert(
                TemplateCategories(
                    "d",
                    "25-08-2022",
                    "26-08-2022"
                )
            )
        }
    }
}