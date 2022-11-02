package com.ft.my_document_organizer.ui.activities.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityMyProfileBinding>() {

    private lateinit var customizedActionBarForActivityBinding: CustomizedActionBarForActivityBinding

    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)

        customizedActionBarForActivityBinding = binding.actionBarLayout

        customizedActionBarForActivityBinding.ivActionBarBack.setOnClickListener {
            finish()
        }

        customizedActionBarForActivityBinding.tvActionBarTitle.text = getString(R.string.my_profile)
        customizedActionBarForActivityBinding.rlToolbarEndBtn.isVisible = true
    }

    override fun setBinding(): ActivityMyProfileBinding = ActivityMyProfileBinding.inflate(layoutInflater)

}