package com.ft.my_document_organizer.worker

import android.content.Context
import android.util.Log
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import com.ft.my_document_organizer.utils.Constants.Companion.WORKER_NAME

class SyncDownloadDataWorker(context: Context, workerParams: WorkerParameters) :
    ListenableWorker(context, workerParams) {

    private val TAG = javaClass.simpleName
    private val workerName = inputData.getString(WORKER_NAME) ?: ""

    override fun startWork(): ListenableFuture<Result> {

        return CallbackToFutureAdapter.getFuture { completer ->

            Log.i(TAG, "Start $workerName")
            completer.set(Result.success(workDataOf(WORKER_NAME to "syncStop")))
        }
    }
}