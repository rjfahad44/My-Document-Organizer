package com.ft.my_document_organizer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.DocApplication.Companion.context
import com.ft.my_document_organizer.databinding.RvItemSettingsBinding
import com.ft.my_document_organizer.utils.listeners.RecyclerViewItemClickListener
import com.ft.my_document_organizer.data.model.UserSettings

class SettingsAdapter(private val recyclerViewItemClickListener: RecyclerViewItemClickListener, var userSettingsList: List<UserSettings>) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemSettingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(userSettingsList[position]){
                binding.tvSettingsTitle.text = this.settingsTitle
                binding.ivSettingsIcon.setImageDrawable(ContextCompat.getDrawable(context, userSettingsList[position].settingsIcon))
            }

            holder.itemView.setOnClickListener{
                recyclerViewItemClickListener.itemClickListener(userSettingsList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return userSettingsList.size
    }
}