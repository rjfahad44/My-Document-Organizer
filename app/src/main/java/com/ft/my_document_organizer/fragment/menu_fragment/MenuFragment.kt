package com.ft.my_document_organizer.fragment.menu_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseFragment
import com.ft.my_document_organizer.databinding.FragmentMenuBinding
import com.ft.my_document_organizer.databinding.FragmentProfileBinding
import com.ft.my_document_organizer.ui.activities.MainActivity
import com.ft.my_document_organizer.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>() {
    lateinit var userViewModel: UserViewModel

    override fun setBinding(): FragmentMenuBinding = FragmentMenuBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = (activity as MainActivity).userViewModel
    }
}