package com.ft.my_document_organizer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.databinding.RvItemAddDocumentBinding

class RvAddDocumentItems(private val items: ArrayList<Int>) : RecyclerView.Adapter<RvAddDocumentItems.ViewHolder>() {
    inner class ViewHolder(val binding: RvItemAddDocumentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemAddDocumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemCounter.text = "${items[position]}"
    }

    override fun getItemCount(): Int = items.size
}