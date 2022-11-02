package com.ft.my_document_organizer.fragment.label_fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ft.my_document_organizer.DocApplication.Companion.sp
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseFragment
import com.ft.my_document_organizer.databinding.FragmentLabelBinding
import com.ft.my_document_organizer.ui.activities.MainActivity
import com.ft.my_document_organizer.ui.adapter.RvLabels
import com.ft.my_document_organizer.ui.viewmodel.UserViewModel
import com.ft.my_document_organizer.utils.AppUtils.saveInfo
import com.ft.my_document_organizer.utils.Constants
import com.ft.my_document_organizer.utils.Constants.Companion.SORT
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LabelFragment : BaseFragment<FragmentLabelBinding>() {

    lateinit var userViewModel: UserViewModel
    lateinit var rvLabels: RvLabels

    init {
        sp = saveInfo(Constants.LABEL_SORT)
    }

    override fun setBinding(): FragmentLabelBinding = FragmentLabelBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = (activity as MainActivity).userViewModel
        initialize()
    }

    private fun initialize() {
        sortLabels()
        binding.toolbar.ivActionBarBack.isVisible = false
        rvLabels = RvLabels()
        binding.toolbar.tvActionBarTitle.text = getString(R.string.label)
        binding.rvLabels.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvLabels.adapter = rvLabels
        rvLabels.setData(mutableListOf("","","","","","","","","",""))
    }

    private fun sortLabels() {
        binding.rlLabelsSortBtn.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.setContentView(R.layout.dialog_sort_all_labels)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val rbAlphabetical = dialog.findViewById<RadioButton>(R.id.rbAlphabetical)
            val rbCreateDate = dialog.findViewById<RadioButton>(R.id.rbCreateDate)
            val rbUpdateDate = dialog.findViewById<RadioButton>(R.id.rbUpdateDate)
            val rbReminder = dialog.findViewById<RadioButton>(R.id.rbReminder)

            val location = IntArray(2)
            binding.rlLabelsSortBtn.getLocationOnScreen(location)

            val wmlp = dialog.window!!.attributes
            wmlp.gravity = Gravity.TOP or Gravity.START
            wmlp.x = location[0] - 60
            wmlp.y = location[1] + 20

            if (sp.getString(SORT, "Alphabetical").equals("Alphabetical")) {
                rbAlphabetical?.isChecked = true
                binding.tvAllLabelSortTitle.text = getString(R.string.alphabetical)
                binding.ivAllLabelSortIcon.setImageResource(R.drawable.ic_baseline_sort_by_alpha_24)
            } else if (sp.getString(SORT, "CreateDate").equals("CreateDate")) {
                rbCreateDate?.isChecked = true
                binding.tvAllLabelSortTitle.text = getString(R.string.create_date)
                binding.ivAllLabelSortIcon.setImageResource(R.drawable.ic_baseline_calendar_today_24)
            } else if (sp.getString(SORT, "UpdateDate").equals("UpdateDate")) {
                rbUpdateDate?.isChecked = true
                binding.tvAllLabelSortTitle.text = getString(R.string.update_date)
                binding.ivAllLabelSortIcon.setImageResource(R.drawable.ic_baseline_edit_calendar_24)
            } else if (sp.getString(SORT, "Reminder").equals("Reminder")) {
                rbReminder?.isChecked = true
                binding.tvAllLabelSortTitle.text = getString(R.string.reminder)
                binding.ivAllLabelSortIcon.setImageResource(R.drawable.ic_baseline_alarm_24)
            }

            rbAlphabetical?.setOnClickListener {
                binding.tvAllLabelSortTitle.text = getString(R.string.alphabetical)
                binding.ivAllLabelSortIcon.setImageResource(R.drawable.ic_baseline_sort_by_alpha_24)
                rbAlphabetical.isChecked = true
                rbCreateDate?.isChecked = false
                rbUpdateDate?.isChecked = false
                rbReminder?.isChecked = false
                sp.edit().putString(SORT, "Alphabetical").apply()
                dialog.dismiss()
            }
            rbCreateDate?.setOnClickListener {
                binding.tvAllLabelSortTitle.text = getString(R.string.create_date)
                binding.ivAllLabelSortIcon.setImageResource(R.drawable.ic_baseline_calendar_today_24)
                rbAlphabetical.isChecked = false
                rbCreateDate.isChecked = true
                rbUpdateDate?.isChecked = false
                rbReminder?.isChecked = false
                sp.edit().putString(SORT, "CreateDate").apply()
                dialog.dismiss()
            }
            rbUpdateDate?.setOnClickListener {
                binding.tvAllLabelSortTitle.text = getString(R.string.update_date)
                binding.ivAllLabelSortIcon.setImageResource(R.drawable.ic_baseline_edit_calendar_24)
                rbAlphabetical.isChecked = false
                rbCreateDate?.isChecked = false
                rbUpdateDate.isChecked = true
                rbReminder?.isChecked = false
                sp.edit().putString(SORT, "UpdateDate").apply()
                dialog.dismiss()
            }

            rbReminder?.setOnClickListener {
                binding.tvAllLabelSortTitle.text = getString(R.string.reminder)
                binding.ivAllLabelSortIcon.setImageResource(R.drawable.ic_baseline_alarm_24)
                rbAlphabetical.isChecked = false
                rbCreateDate?.isChecked = false
                rbUpdateDate?.isChecked = false
                rbReminder.isChecked = true
                sp.edit().putString(SORT, "Reminder").apply()
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}