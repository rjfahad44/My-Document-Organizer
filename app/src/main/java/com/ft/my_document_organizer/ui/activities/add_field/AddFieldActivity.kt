package com.ft.my_document_organizer.ui.activities.add_field

import android.app.Dialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivityAddFieldBinding
import com.ft.my_document_organizer.databinding.RvItemAddFieldBinding
import com.ft.my_document_organizer.ui.activities.add_document.AddDocumentTextOnlyActivity
import com.ft.my_document_organizer.ui.activities.camera.CameraActivity
import com.ft.my_document_organizer.ui.adapter.RvFieldType
import com.ft.my_document_organizer.utils.listeners.RvFieldTypeClickListener

class AddFieldActivity : BaseActivity<ActivityAddFieldBinding>(), RvFieldTypeClickListener {

    private lateinit var rvFieldType: RvFieldType
    private lateinit var dialogFieldType: Dialog
    val items = mutableListOf("Text Field", "Text Area", "Date", "Numbers", "Checkbox", "Radio Button")

    override fun setBinding(): ActivityAddFieldBinding = ActivityAddFieldBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.toolbar.ivActionBarBack.setOnClickListener { finish() }
        binding.toolbar.tvActionBarTitle.text = getString(R.string.add_field)
        binding.toolbar.rlToolbarEndBtn.isVisible = false

        rvFieldType = RvFieldType(this)
        binding.tvSelectOption.setOnClickListener { selectFieldDialog() }
    }

    private fun selectFieldDialog() {
        val dialog = Dialog(this@AddFieldActivity)
        dialog.setContentView(R.layout.dialog_add_filed)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogFieldType = dialog
        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)


        val rv = dialog.findViewById<RecyclerView>(R.id.rvFieldItems)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter =rvFieldType
        rvFieldType.setData(items)
        dialog.show()
    }

    override fun itemClickListener(any: Any) {
        dialogFieldType.dismiss()
        val view = any as RvItemAddFieldBinding
        binding.tvSelectOption.text = view.tvSelectField.text
    }
}