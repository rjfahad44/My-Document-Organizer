package com.ft.my_document_organizer.ui.activities.add_document

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivityAddDocumentTextOnlyBinding
import com.ft.my_document_organizer.ui.activities.add_field.AddFieldActivity
import com.ft.my_document_organizer.ui.activities.add_field.DocumentTypeFieldActivity

class AddDocumentTextOnlyActivity : BaseActivity<ActivityAddDocumentTextOnlyBinding>() {

    override fun setBinding(): ActivityAddDocumentTextOnlyBinding = ActivityAddDocumentTextOnlyBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        initialize()
    }

    private fun initialize(){
        binding.toolbar.ivActionBarBack.isVisible = false
        binding.toolbar.tvActionBarTitle.text = "Add Document 1/2"
        binding.toolbar.rlToolbarEndBtn.isVisible = false

        binding.rlDiscard.setOnClickListener { finish() }
        binding.rlNext.setOnClickListener {
            //startActivity(Intent(this@AddDocumentTextOnlyActivity, DocumentTypeFieldActivity::class.java))
            startActivity(Intent(this@AddDocumentTextOnlyActivity, AddFieldActivity::class.java))
        }
    }
}