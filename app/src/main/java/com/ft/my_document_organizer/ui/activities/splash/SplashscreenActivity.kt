package com.ft.my_document_organizer.ui.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivitySplashscreenBinding
import com.ft.my_document_organizer.ui.activities.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : BaseActivity<ActivitySplashscreenBinding>() {

    override fun setBinding(): ActivitySplashscreenBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashscreenActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }
}