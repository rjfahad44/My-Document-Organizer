package com.ft.my_document_organizer.ui.activities.settings

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.ui.adapter.SettingsAdapter
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivitySettingsBinding
import com.ft.my_document_organizer.databinding.CustomizedActionBarForActivityBinding
import com.ft.my_document_organizer.utils.listeners.RecyclerViewItemClickListener
import com.ft.my_document_organizer.data.model.UserSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>(), RecyclerViewItemClickListener {

    private val TAG : String = SettingsActivity::javaClass.name
    private lateinit var toolbarBinding: CustomizedActionBarForActivityBinding

    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var userSettingsList : List<UserSettings>

    override fun setBinding(): ActivitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)

        toolbarBinding = binding.actionBarLayout
        toolbarBinding.tvActionBarTitle.text = getString(R.string.settings)
        toolbarBinding.ivActionBarBack.setOnClickListener {
            finish()
        }

        generateSettingsList()
    }

    private fun generateSettingsList() {
        loadUserSettings()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvSettings.layoutManager = layoutManager
        settingsAdapter = SettingsAdapter(this, userSettingsList)
        binding.rvSettings.adapter = settingsAdapter
    }

    private fun loadUserSettings() {
        userSettingsList = listOf(
            UserSettings(R.drawable.ic_account, getString(R.string.acount)),
            UserSettings(R.drawable.ic_account, getString(R.string.google_account)),
            UserSettings(R.drawable.ic_media_and_storage, getString(R.string.media_and_storage)),
            UserSettings(R.drawable.ic_sync, getString(R.string.sync)),
            UserSettings(R.drawable.ic_fingerprint, getString(R.string.biometric_or_face_id)),
            UserSettings(R.drawable.ic_theme, getString(R.string.dark_mode)),
            UserSettings(R.drawable.ic_notification, getString(R.string.notification)),
            UserSettings(R.drawable.ic_privacy, getString(R.string.privacy)),
            UserSettings(R.drawable.ic_about, getString(R.string.about)),
            UserSettings(R.drawable.ic_help, getString(R.string.general)),
            UserSettings(R.drawable.ic_help, getString(R.string.help)),
        )
    }

    override fun itemClickListener(value: Any) {
        val userSettings : UserSettings = value as UserSettings
        startActivity(Intent(this@SettingsActivity, SubSettingsListActivity::class.java).putExtra("settings_title", userSettings.settingsTitle))
    }
}