package com.ft.my_document_organizer.ui.activities.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.ui.adapter.SettingsAdapter
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivitySubSettingsListBinding
import com.ft.my_document_organizer.databinding.CustomizedActionBarForActivityBinding
import com.ft.my_document_organizer.utils.listeners.RecyclerViewItemClickListener
import com.ft.my_document_organizer.data.model.UserSettings
import com.ft.my_document_organizer.ui.activities.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SubSettingsListActivity : BaseActivity<ActivitySubSettingsListBinding>(), RecyclerViewItemClickListener {

    private val TAG : String = SubSettingsListActivity::javaClass.name
    private lateinit var toolbarBinding: CustomizedActionBarForActivityBinding

    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var userSettingsList : List<UserSettings>

    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)

        toolbarBinding = binding.actionBarLayoutSettingsDetails

        toolbarBinding.ivActionBarBack.setOnClickListener { finish() }

        val settingsTitle : String = intent.getStringExtra("settings_title")!!
        toolbarBinding.tvActionBarTitle.text = settingsTitle
        toolbarBinding.rlToolbarEndBtn.isVisible = false


        generateSettingsList(settingsTitle)
    }

    private fun generateSettingsList(settingsTitle : String) {
        if(settingsTitle == "Account"){
            loadAccountSettings()
        }else{
            loadUserSettings()
        }

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvSettingsDetails.layoutManager = layoutManager
        settingsAdapter = SettingsAdapter(this, userSettingsList)
        binding.rvSettingsDetails.adapter = settingsAdapter
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

    private fun loadAccountSettings() {
        userSettingsList = listOf(
            UserSettings(R.drawable.ic_account, getString(R.string.my_profile)),
            UserSettings(R.drawable.ic_account, getString(R.string.change_password)),
            UserSettings(R.drawable.ic_media_and_storage, getString(R.string.my_subscription)),
            UserSettings(R.drawable.ic_sync, getString(R.string.sign_out))
        )
    }

    override fun setBinding(): ActivitySubSettingsListBinding = ActivitySubSettingsListBinding.inflate(layoutInflater)
    override fun itemClickListener(value: Any) {
        val userSettings : UserSettings = value as UserSettings
        Timber.v(userSettings.settingsTitle)

        if(userSettings.settingsTitle == "My Profile"){
            startActivity(Intent(this@SubSettingsListActivity, ProfileActivity::class.java))
        }else{
            startActivity(Intent(this@SubSettingsListActivity, SettingsDetailsActivity::class.java).putExtra("sub_settings_title", userSettings.settingsTitle))
        }
    }

}