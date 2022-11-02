package com.ft.my_document_organizer.ui.activities.add_document

import android.app.Dialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivityAddDocumentBinding
import com.ft.my_document_organizer.ui.adapter.RvAddDocumentItems
import com.ft.my_document_organizer.ui.adapter.RvCountryAdapter
import com.ft.my_document_organizer.ui.adapter.RvDocumentTypes
import com.ft.my_document_organizer.ui.viewmodel.DocViewModel
import com.ft.my_document_organizer.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_select_document_type.*

@AndroidEntryPoint
class AddDocumentActivity : BaseActivity<ActivityAddDocumentBinding>() {

    val viewModel: DocViewModel by viewModels()
    val userViewModel: UserViewModel by viewModels()
    lateinit var rvDocumentTypes: RvDocumentTypes

    override fun setBinding(): ActivityAddDocumentBinding = ActivityAddDocumentBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.toolbar.rlToolbarEndBtn.isVisible = false
        binding.toolbar.ivDeleteIcon.isVisible = true
        binding.toolbar.tvActionBarTitle.text = "Add Document 1/2"
        binding.toolbar.ivActionBarBack.isVisible = false
        binding.toolbar.tvToolbarEndBtnText.isVisible = false
        binding.toolbar.ivToolbarEndBtnIcon.setImageResource(R.drawable.ic_baseline_delete_outline_24)

        binding.rvAddDocImage.layoutManager = GridLayoutManager(this, 2)
        binding.rvAddDocImage.adapter = RvAddDocumentItems(arrayListOf(1, 2, 3))

        binding.toolbar.ivDeleteIcon.setOnClickListener {
            showDeleteDialog()
        }

        binding.rlDiscard.setOnClickListener { finish() }
        binding.tvSelectDocumentType.setOnClickListener {
            selectDocumentDialog()
        }
        binding.rlNext.setOnClickListener { startActivity(Intent(this@AddDocumentActivity, AddDocumentOtherInfoActivity::class.java)) }
    }

    private fun showDeleteDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_delete_document)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<TextView>(R.id.tvYesDiscard).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.tvNoKeep).setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    private fun selectDocumentDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_select_document_type)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        //setting dialog height and width dynamically
        val displayRectangle = Rect()
        dialog.window?.decorView?.getWindowVisibleDisplayFrame(displayRectangle)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        /*val location = IntArray(2)
        binding.tvSelectDocumentType.getLocationOnScreen(location)
        val wmlp = dialog.window!!.attributes
        wmlp.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        wmlp.x = location[1]
        wmlp.y = location[0]*/

        dialog.rvDocumentType.layoutManager = LinearLayoutManager(this)
        rvDocumentTypes = RvDocumentTypes()
        dialog.rvDocumentType.adapter = rvDocumentTypes
        rvDocumentTypes.setData(getDocTypeList())

        dialog.findViewById<EditText>(R.id.etSearchDocType)
            .doOnTextChanged { text, start, before, count ->
                filter(text, getDocTypeList(), rvDocumentTypes)
            }

        dialog.ivClose.setOnClickListener { dialog.dismiss() }

     dialog.show()
    }

    private fun filter(
        text: CharSequence?,
        docTypeList: MutableList<String>,
        rvDocumentTypes: RvDocumentTypes
    ) {
        val newList: MutableList<String> = mutableListOf()
        for (item in docTypeList) {
            if (item.lowercase().contains(text!!)) {
                newList.add(item)
            }
        }
        rvDocumentTypes.setData(newList)
    }

    private fun getDocTypeList(): MutableList<String> {
        return mutableListOf(
            "Driving License",
            "Passport",
            "Fitness Certificate",
            "Bills",
            "Invoice"
        )
    }
}