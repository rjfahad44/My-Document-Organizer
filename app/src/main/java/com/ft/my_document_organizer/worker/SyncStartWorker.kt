package com.ft.my_document_organizer.worker

import android.content.Context
import android.util.Log
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import com.ft.my_document_organizer.utils.Constants.Companion.WORKER_NAME
import java.util.HashMap

class SyncStartWorker(context: Context, workerParams: WorkerParameters) :
    ListenableWorker(context, workerParams) {

    private val TAG = javaClass.simpleName
    private val workerName = inputData.getString(WORKER_NAME) ?: ""

    override fun startWork(): ListenableFuture<Result> {

        return CallbackToFutureAdapter.getFuture { completer ->

            val syncMap = HashMap<String, String>()
            syncMap["action"] = "start"
            syncMap["table_name"] = ""
            syncMap["data"] = ""
            syncMap["limit"] = ""
            syncMap["offset"] = ""

            Log.i(TAG, "Start $workerName")

            /*sync(syncMap, object : ApiListener {
                override fun onSuccess(
                    type: String,
                    data: String,
                    statusCode: String,
                    message: String,
                    response: JsonObject?
                ) {
                    if (statusCode == "200"){
                        Log.i(TAG, "onSuccess sync statusCode : $statusCode")
                        Log.i(TAG, "onSuccess sync : ${response}")
                        completer.set(Result.success(workDataOf(WORKER_NAME to "syncUploadData")))
                    }else{
                        Log.i(TAG, "onSuccess sync statusCode : $statusCode")
                        Log.i(TAG, "onSuccess sync : ${response}")
                        completer.set(Result.failure())
                    }
                }

                override fun onError(errorCode: String, statusCode: String, message: String) {
                    Log.i(TAG, "onError sync statusCode : $statusCode")
                    Log.i(TAG, "onError sync errorCode : $errorCode")
                    Log.i(TAG, "onError sync message : $message")
                }

                override fun onFailure(call: Call<JsonObject>, throwable: Throwable) {
                    Log.i(TAG, "onFailure sync message : ${throwable.message}")
                    completer.setException(throwable)
                    completer.set(Result.failure())
                }
            })*/
        }
    }
}