package com.ft.my_document_organizer.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ft.my_document_organizer.DocApplication.Companion.context
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.databinding.RvItemCountryBinding
import com.ft.my_document_organizer.databinding.RvItemSettingsBinding
import com.ft.my_document_organizer.utils.listeners.RvCountryItemClickListener
import java.util.ArrayList

class RvCountryAdapter(private val countryItemClickListener: RvCountryItemClickListener) : RecyclerView.Adapter<RvCountryAdapter.ViewHolder>() {

    private val edtList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCountry.text = edtList[position]
        holder.binding.tvCountry.setOnClickListener {
            countryItemClickListener.itemClickListener(holder.binding.tvCountry.text.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(arrayList: ArrayList<String>) {
        edtList.clear()
        edtList.addAll(arrayList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = edtList.size

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(val binding: RvItemCountryBinding) : RecyclerView.ViewHolder(binding.root)
}