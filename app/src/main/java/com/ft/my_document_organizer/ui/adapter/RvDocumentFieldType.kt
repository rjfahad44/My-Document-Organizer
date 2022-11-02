package com.ft.my_document_organizer.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.databinding.RvItemDocFieldTypeBinding
import com.ft.my_document_organizer.utils.listeners.RvDocFieldClickListener

class RvDocumentFieldType(private val rvDocFieldClickListener: RvDocFieldClickListener) : RecyclerView.Adapter<RvDocumentFieldType.ViewHolder>() {

    private var items: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newItems: MutableList<String>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemDocFieldTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvFieldType.text = items[position]
        holder.binding.ivThreeDots.setOnClickListener {
            rvDocFieldClickListener.threeDotClickListener(holder.binding)
        }
        holder.binding.ivModeItemIcon.setOnLongClickListener {
            rvDocFieldClickListener.moveIconClickListener(holder.binding.ivModeItemIcon)
            true
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: RvItemDocFieldTypeBinding) : RecyclerView.ViewHolder(binding.root)
}