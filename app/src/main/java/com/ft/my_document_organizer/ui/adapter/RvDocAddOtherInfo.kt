package com.ft.my_document_organizer.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.databinding.RvItemAddDocumentBinding
import com.ft.my_document_organizer.databinding.RvItemAllLabelsBinding
import com.ft.my_document_organizer.databinding.RvItemDocInfoBinding
import com.ft.my_document_organizer.databinding.RvItemDocumentTypeBinding

class RvDocAddOtherInfo : RecyclerView.Adapter<RvDocAddOtherInfo.ViewHolder>() {

    private var items: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newItems: MutableList<String>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemDocInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: RvItemDocInfoBinding) : RecyclerView.ViewHolder(binding.root)
}