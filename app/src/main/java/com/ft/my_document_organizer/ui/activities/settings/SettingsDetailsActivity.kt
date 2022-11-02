package com.ft.my_document_organizer.ui.activities.settings

import android.os.Bundle
import android.view.View
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivitySettingsDetailsBinding
import com.ft.my_document_organizer.databinding.CustomizedActionBarForActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsDetailsActivity : BaseActivity<ActivitySettingsDetailsBinding>() {

    private lateinit var customizedActionBarForActivityBinding: CustomizedActionBarForActivityBinding

    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)

        customizedActionBarForActivityBinding = binding.actionBarLayout

        customizedActionBarForActivityBinding.ivActionBarBack.setOnClickListener {
            finish()
        }

        val settingsTitle : String = intent.getStringExtra("sub_settings_title")!!
        customizedActionBarForActivityBinding.tvActionBarTitle.text = settingsTitle
        customizedActionBarForActivityBinding.rlToolbarEndBtn.visibility = View.GONE
    }

    override fun setBinding(): ActivitySettingsDetailsBinding = ActivitySettingsDetailsBinding.inflate(layoutInflater)

}