package com.ft.my_document_organizer.fragment.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.ft.my_document_organizer.R
import com.ft.my_document_organizer.base.BaseFragment
import com.ft.my_document_organizer.databinding.FragmentHomeBinding
import com.ft.my_document_organizer.databinding.FragmentProfileBinding
import com.ft.my_document_organizer.ui.activities.MainActivity
import com.ft.my_document_organizer.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var userViewModel: UserViewModel

    override fun setBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = (activity as MainActivity).userViewModel
        initialize()
    }
    private fun initialize(){
        binding.toolbar.ivActionBarBack.isVisible = false
        binding.toolbar.tvActionBarTitle.text = "Home"
    }
}