package com.ft.my_document_organizer.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ft.my_document_organizer.utils.Constants.Companion.WORKER_NAME

class SyncUploadFilesWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val TAG = javaClass.simpleName
    private val workerName = inputData.getString(WORKER_NAME)
    override fun doWork(): Result {

        Log.i(TAG, "Task $workerName started")
        Thread.sleep(2000)
        Log.i(TAG, "Task $workerName  Completed")

        return Result.success(workDataOf(WORKER_NAME to "syncUploadCompleted"))
        //return Result.success()
        //return Result.retry()
        //return Result.failure()
    }
}
