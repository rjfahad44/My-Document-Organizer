package com.ft.my_document_organizer.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import com.ft.my_document_organizer.R

class Loader(activity: Activity) {

    private val progressDialog = Dialog(activity)
    var progressBar: ProgressBar? = null

    fun showLoader() {
        progressDialog.setContentView(R.layout.popup_progressbar)
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)
        progressBar = progressDialog.findViewById(R.id.progressBar)
        progressDialog.show()
    }

    fun hideLoader() {
        progressDialog.dismiss()
    }
}