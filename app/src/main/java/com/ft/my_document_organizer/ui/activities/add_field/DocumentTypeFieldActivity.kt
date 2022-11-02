package com.ft.my_document_organizer.ui.activities.add_field

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseActivity
import com.ft.my_document_organizer.databinding.ActivityDocumentTypeFieldBinding
import com.ft.my_document_organizer.databinding.RvItemDocFieldTypeBinding
import com.ft.my_document_organizer.ui.adapter.RvDocumentFieldType
import com.ft.my_document_organizer.utils.listeners.RvDocFieldClickListener
import java.util.*

class DocumentTypeFieldActivity : BaseActivity<ActivityDocumentTypeFieldBinding>(), RvDocFieldClickListener{

    private lateinit var rvDocumentFieldType: RvDocumentFieldType
    private lateinit var itemTouchHelper: ItemTouchHelper
    private var items = mutableListOf(
        "Passport Holders Name*","Passport ID","Issue Date*","Expire Date*"
    )

    val itemMoveCallBack = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                ItemTouchHelper.START or ItemTouchHelper.END, 0){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            Collections.swap(items, fromPosition, toPosition)
            rvDocumentFieldType.notifyItemMoved(fromPosition, toPosition)
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }

    override fun setBinding(): ActivityDocumentTypeFieldBinding = ActivityDocumentTypeFieldBinding.inflate(layoutInflater)
    override fun onViewReady(savedInstanceState: Bundle?) {
        super.onViewReady(savedInstanceState)

        initialize()
    }
    private fun initialize(){
        binding.toolbar.ivActionBarBack.setOnClickListener { finish() }
        binding.toolbar.tvActionBarTitle.text = getString(R.string.document_type_field)
        binding.toolbar.rlToolbarEndBtn.isVisible = false

        rvDocumentFieldType = RvDocumentFieldType(this)
        binding.rvDocFieldType.layoutManager = LinearLayoutManager(this)
        binding.rvDocFieldType.adapter = rvDocumentFieldType
        rvDocumentFieldType.setData(items)
        itemTouchHelper = ItemTouchHelper(itemMoveCallBack)
    }

    override fun threeDotClickListener(any: Any) {
        val view: RvItemDocFieldTypeBinding = any as RvItemDocFieldTypeBinding
        val popupMenu = PopupMenu(this, view.viewMid)
        popupMenu.inflate(R.menu.popup_doc_field_three_dot_menu)
        popupMenu.setForceShowIcon(true)

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.editPopupMenuId ->{

                    true
                }
                R.id.deletePopupMenuId->{

                    true
                }
                else ->{
                    false
                }
            }
        }
        popupMenu.show()
    }

    override fun moveIconClickListener(any: Any) {
        itemTouchHelper.attachToRecyclerView(binding.rvDocFieldType)
    }
}