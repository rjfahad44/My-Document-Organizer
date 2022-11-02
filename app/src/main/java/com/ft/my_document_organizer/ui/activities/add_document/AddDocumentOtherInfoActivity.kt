package com.ft.my_document_organizer.ui.activities.add_document

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivityAddDocumentOtherInfoBinding
import com.ft.my_document_organizer.ui.adapter.RvDocAddOtherInfo
import com.ft.my_document_organizer.ui.viewmodel.DocViewModel
import com.ft.my_document_organizer.ui.viewmodel.UserViewModel

class AddDocumentOtherInfoActivity : BaseActivity<ActivityAddDocumentOtherInfoBinding>() {

    val viewModel: DocViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()
    private lateinit var rvDocAddOtherInfo: RvDocAddOtherInfo

    override fun setBinding(): ActivityAddDocumentOtherInfoBinding = ActivityAddDocumentOtherInfoBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.toolbar.ivActionBarBack.isVisible = false
        binding.toolbar.tvActionBarTitle.text = "Add Document 2/2"
        binding.toolbar.rlToolbarEndBtn.isVisible = false
        binding.rlBack.setOnClickListener { finish() }

        rvDocAddOtherInfo = RvDocAddOtherInfo()
        binding.rvFiledInfoItems.layoutManager = LinearLayoutManager(this)
        binding.rvFiledInfoItems.adapter = rvDocAddOtherInfo
        rvDocAddOtherInfo.setData(mutableListOf("","","","","","","","","","",""))
    }
}