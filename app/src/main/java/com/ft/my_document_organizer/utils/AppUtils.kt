package com.ft.my_document_organizer.utils

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import androidx.room.TypeConverter
import com.ft.my_document_organizer.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.ft.my_document_organizer.DocApplication.Companion.context
import com.ft.my_document_organizer.utils.Constants.Companion.EMAIL_ACCOUNTS_DATABASE_CONTENT_URI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.util.*
import kotlin.collections.HashMap


object AppUtils {

    @SuppressLint("NewApi")
    fun dataTime(): SimpleDateFormat =
        SimpleDateFormat("yyyy-MM-dd  HH:mm:ss z", Locale.getDefault())

    @SuppressLint("Range", "Recycle")
    fun getPrimaryEmail(): ArrayList<String>? {
        val names = ArrayList<String>()
        val cr: ContentResolver = context.contentResolver
        val cursor: Cursor? = cr.query(
            EMAIL_ACCOUNTS_DATABASE_CONTENT_URI, null,
            null, null, null
        )
        if (cursor == null) {
            Timber.d("EMAIL_ADDRESS", "Cannot access email accounts database")
            return null
        }
        if (cursor.count <= 0) {
            Timber.d("EMAIL_ADDRESS", "No accounts")
            return null
        }
        while (cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("emailAddress")))
            Timber.d("EMAIL_ADDRESS", cursor.getString(cursor.getColumnIndex("emailAddress")))
        }
        return names
    }


    fun getAllGoogleAccount(): MutableList<String> {
        val googleAccountList = arrayListOf<String>()
        val accounts = AccountManager.get(context).accounts

        Timber.d("GOOGLE_ACCOUNT", "Size: ${accounts.size}")
        for (account in accounts) {
            val possibleEmail = account.name
            val type = account.type
            if (type == "com.google") {
                googleAccountList.add(possibleEmail)
                Timber.d("GOOGLE_ACCOUNT", "Emails: $possibleEmail")
                break
            }
        }
        return googleAccountList
    }

    fun googleSigningBuilder(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        return context.let { GoogleSignIn.getClient(it, gso) }
    }

    fun isGoogleSigning(): Boolean = GoogleSignIn.getLastSignedInAccount(context) != null

    fun parseJson(stringJson: String) {
        val jsonObject = JSONObject(stringJson)
        val jsonArray: JSONArray by lazy {
            jsonObject.optJSONArray("sessions")
        }
        var firstObject = jsonArray[0]
        //Log.i("JSON", "$jsonArray")
    }

    @TypeConverter
    fun convertListToJsonArray(list: MutableList<Any>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertMapToJson(map: HashMap<String, String>): String? = Gson().toJson(map)

    fun saveInfo(key: String): SharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE)

    fun showPopupProgressSpinner(isShowing: Boolean) {
        val progressDialog = Dialog(context)
        val progressBar: ProgressBar
        if (isShowing) {
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog.setContentView(R.layout.popup_progressbar)
            progressDialog.setCancelable(false)
            progressDialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            progressBar = progressDialog.findViewById(R.id.progressBar)
            progressBar.indeterminateDrawable.setColorFilter(
                Color.parseColor("#ff6700"),
                PorterDuff.Mode.MULTIPLY
            )
            progressDialog.show()
        } else if (!isShowing) {
            progressDialog.dismiss()
        }
    }

    fun unitTest() {
        //ldpi = 0.75
        for (i in 0..250) {
            Timber.tag("DEMO").d("<dimen name=\"_${i}dp\">${i * .75}dp</dimen>")
        }
        //mdpi = 1.0
        for (i in 0..250) {
            Timber.tag("DEMO").d("<dimen name=\"_${i}dp\">${i}dp</dimen>")
        }
        //hdpi = 1.5
        for (i in 0..250) {
            Timber.tag("DEMO").d( "<dimen name=\"_${i}dp\">${i / 1.3}dp</dimen>")
        }
        //xhdpi = 2.0
        for (i in 0..250) {
            Timber.tag("DEMO").d( "<dimen name=\"_${i}dp\">${i / 1.80}dp</dimen>")
        }
        /*
        //xxhdpi = 3.0
        for (i in 0..250) {
            Timber.tag("DEMO").d( "<dimen name=\"_${i}dp\">${i / 1.80}dp</dimen>")
        }
        //xxxhdpi = 4.0
        for (i in 0..250) {
            Timber.tag("DEMO").d( "<dimen name=\"_${i}dp\">${i / 1.80}dp</dimen>")
        }
        */
    }

    // todo: this method can return null handle
    fun hashMapToRequestBody(dataMap: HashMap<String, String>): RequestBody{
        return convertMapToJson(dataMap)?.let {
            RequestBody.create(
                "Content-Type:application/json".toMediaTypeOrNull(),
                it
            )
        }!!
    }


    fun successDialog(successMessage: String, activity: Activity) {
        val dialog = Dialog(activity)
        dialog.setContentView(R.layout.dialog_login_successful)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<TextView>(R.id.tvSubTitle).text = successMessage
        dialog.setCancelable(false)

        dialog.findViewById<TextView>(R.id.tvContinueBtn).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun errorDialog(errorMessage: String, activity: Activity) {
        val dialog = Dialog(activity)
        dialog.setContentView(R.layout.dialog_login_failed)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            (displayRectangle.width() * 0.90f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.setCancelable(false)

        dialog.findViewById<TextView>(R.id.tvSubTitle).text = errorMessage

        dialog.findViewById<TextView>(R.id.tvTryAgain).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}