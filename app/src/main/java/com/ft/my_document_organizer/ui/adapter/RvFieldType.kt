package com.ft.my_document_organizer.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.databinding.RvItemAddFieldBinding
import com.ft.my_document_organizer.databinding.RvItemDocFieldTypeBinding
import com.ft.my_document_organizer.utils.listeners.RvDocFieldClickListener
import com.ft.my_document_organizer.utils.listeners.RvFieldTypeClickListener

class RvFieldType(private val rvFieldTypeClickListener: RvFieldTypeClickListener) : RecyclerView.Adapter<RvFieldType.ViewHolder>() {

    private var items: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newItems: MutableList<String>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemAddFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvSelectField.text = items[position]
        holder.binding.rlRootLayout.setOnClickListener {
            rvFieldTypeClickListener.itemClickListener(holder.binding)
        }
    }

    override fun getItemCount(): Int = items.size
    inner class ViewHolder(val binding: RvItemAddFieldBinding) : RecyclerView.ViewHolder(binding.root)
}